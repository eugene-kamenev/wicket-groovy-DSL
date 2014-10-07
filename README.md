#Apache Wicket Groovy DSL Project 0.4

### Overview
Apache Wicket is great, and Groovy is also great. This project tries to combine the power of both. However, sometimes Apache Wicket code become damn verbose.
But with some little, yet powerful Groovy DSL written, we can extend Wicket to simplify common tasks, and to delete over 30-40% of verbose code.

###Versions used:
    1. Apache Wicket 6.17.0
    2. Groovy 2.3.7 (Yes, with Traits :)
    3. Twitter bootstap for some beautify
    4. And others

###Modules:
    1. Core - core package
    2. Gems - package where I will collect some custom groovy components with its own dsl methods
    3. Examples - package contains simple examples of dsl-core and gems packages

###Changelog:
0.4 - After working with this DSL in real world applications we found some bugs, fixed them. Improved @CompileStatic support,
improved IDE support (Eclipse users can you tell me how it works in?). GroovyDataTable bug fixes. New methods added, see changes in
[WicketDSL.groovy](core/src/main/groovy/wicket/groovy/WicketDSL.groovy). All files in examples now are with @CompileStatic annotation.
0.3 - Main changes from first version, migrated to groovy extension feature
###Usage
```groovy
...
    form('wicketForm') {
        model(compoundModel(this))
        text 'input1'
        text 'input2'
        email 'emailInput'
        url 'urlInput'
        password 'passwordInput'
        
        submit { 
            println this.input1 + this.input2
        }
        visible { 
            this.input1 != this.input2 
        }
        error {  
            println 'Form validation failed'
        } 
    }

    ['one', 'two', 'three'].listView('listView') { ListItem<String> item ->
        item.label 'itemLabel', item.model
    }
```
Instead of verbose Java:
```java
...
   Form form = new Form("wicketForm", new CompoundPropertyModel(this)) {
        @Override
        public void onSubmit(){
            System.out.println(MyPage.this.input1 + MyPage.this.input2);
        }
        @Override
        public boolean isVisible() {
            // just for example :)
            !MyPage.this.input1.equals(MyPage.this.input2);
        }
        @Override
        public void onError() {
            System.out.println("Form validation failed");
        }
   }
   form.add(new TextField("input1"));
   form.add(new TextField("input2"));
   form.add(new EmailTextField("emailInput"));
   form.add(new UrlTextField("urlInput"));
   form.add(new PasswordTextField("emailInput"));
   add(form);
   ArrayList<String> list = new ArrayList<>();
   list.add("one"); list.add("two"); list.add("three");
   add(new ListView("listView", list) {
        @Override
        protected void populateItem(ListItem<T> item) {
            item.add(new Label("itemLabel", item.getModel()));
        }
   })
```
### Environment
I not sure why, but IntelliJ IDEA "Make" does not correctly work with it, so if you want to run examples, you should create
Run Configuration without it, use gradle build instead. Another options I am using: Oracle JDK 8, IntelliJ IDEA 14, Jetty 9.0.7

###DSL Methods
You can start using these methods in any places of your components, just remember that usually closure delegates to it.

###WebMarkupContainer
```groovy
div('markupId') { div ->
     // another components here auto-attached to WebMarkupContainer
}
```
###AjaxLink
```groovy
ajaxLink('markupId') { AjaxLink<T> link ->
     // link content
     click { AjaxRequestTarget t, AjaxLink<T> link ->
         // onClick event here
     }
}
```
###Label
```groovy
label('markupId') { label ->
     // label
}
```
###Form
```groovy
form('markupId') { form ->
     // form content
     submit { Form<T> form ->
         // onSubmit event here
     }
}
```
###AjaxForm
```groovy
ajaxForm('markupId') { form ->
     submit { AjaxRequestTarget t, Form<T> form ->
         // onSubmit event here
     }
}
```
###AjaxEvent
```groovy
label('markupId').ajaxEvent('onclick') { AjaxRequestTarget target ->
    // process event here
}
```
###TypeSafe PropertyModel
```groovy
def model = new Model<User>(user)
label('username').setModel(model.property { it.name } ) // property method return IModel<String>
label('eventCount').setModel(model.property { it.eventCount } ) // property method return IModel<Integer>
label('town').setModel(model.property { it.town }) // property method return IModel<Town>

// or if we want to get type safe property model from component

def fragment = new UserFragment() // there is a model with User object inside
label('name').setModel(fragment.property(User.class) { it.name })
label('townTitle').setModel(fragment.property(User.class) { it.town.title })
```
#### How TypeSafe PropertyModel works
[WicketDSL](core/src/main/groovy/wicket/groovy/WicketDSL.groovy) property method closure is declared as:
```groovy
static <I extends Serializable, T extends Component, S extends Serializable> IModel<I> property(T component, Class<S> clazz,
                                                                                                    @DelegatesTo(value = S, strategy = Closure.DELEGATE_ONLY)
                                                                                                    @ClosureParams(value = FromString, options = 'S')
                                                                                                    Closure<I> closure)
```
So, when you call it with User.class, your IDE and compiler will think that closure delegates to the User object instance. But we are fooling them all.
Actually your closure will be delegated to [TypeSafeModelBuilder](core/src/main/groovy/wicket/groovy/core/helpers/TypeSafeModelBuilder.groovy) that will build object property notation string.
For example if we will write:
```groovy
fragment.property(User.class) { it.town.title }
```
TypeSafeModelBuilder will build the string 'town.title' and create new PropertyModel<I>('town.title', model)

Also DSL contains useful shortcut methods like this:

```groovy
serializableObject.toLoadModel() // wrap object with loadable detachable model
serializableObject.toLabel('markupId') // convert to Label
list.toDropDown('markupId') // convert to DropDownChoice
```
And much more. Take a look at [WicketDSL class](core/src/main/groovy/wicket/groovy/WicketDSL.groovy)

### Gems package
For now this package includes only GroovyDataTable (Wicket with jQuery DataTables integrated) and InfiniteScrollTable (jQuery DataTable with infinite scrolling feature) classes created by me.
These table components are self-written, no wicket-stuff datatable components used.
Use them like this:
```groovy
table('tableId') {
    cell('id')
    cell('name')
    cell('surname')
    cell('action') {
        item { id, imodel ->
                new PersonActionPanel(id, imodel)
        }
        it.css {
                    ['btn', 'btn-primary']
        }
    }
    
    list {
        Person.getAll()
    }
}
```
and infinite scrolling one:
```groovy
infiniteTable('tableId') {
    cell('id')
    cell('name')
    cell('surname')
    cell('action') {
        item { id, imodel ->
            new PersonActionPanel(id, imodel)
        }
        it.css {
            ['btn', 'btn-primary']
        }
    }
    list { offset ->
            Person.getAll(offset)
    }
}
```

#### Operators overloaded:
#####'parent + child ' => parent.add(child)
#####'parent - child' => parent.remove(child)
#####'component >> another' => component.replaceWith(another)
#####'component << another' => component.addOrReplace(another)

##Thanks

### - to great Groovy Community, and especially to Andrey Bloschetsov.
### - to Martin Dashorst, for post about this project at http://wicketinaction.com
### - to Martin Grigorov, for providing comments and suggestions, making this project better

Feel free to contact me, to advice me how to make this DSL better.

##Help
I need help in testing this DSL, also I need your opinion on this
