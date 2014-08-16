#Apache Wicket Groovy DSL Project 0.3

### Overview
Apache Wicket is great, and Groovy is also great. This project tries to combine the power of both. However, sometimes Apache Wicket code become damn verbose.
But with some little, yet powerful Groovy DSL written, we can extend Wicket to simplify common tasks, and to delete over 30-40% of verbose code.

###Versions used:
    1. Apache Wicket 6.16.0
    2. Groovy 2.3.6 (Yes, with Traits :)
    3. Twitter bootstap for some beautify

###Modules:
    1. Core - core package
    2. Gems - package where I will collect some custom groovy components with its own dsl methods
    3. Examples - package contains simple examples of dsl-core and gems packages

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
What do you think about this? It is groovy way.

Also DSL contains useful shortcut methods like this:

```groovy
serializableObject.toLoadModel() // wrap object with loadable detachable model
serializableObject.toLabel('markupId') // convert to Label
list.toDropDown('markupId') // convert to DropDownChoice
```
And much more. Take a look at [WicketDSL class](core/src/main/groovy/wicket/groovy/WicketDSL.groovy)

### Gems package
For now this package includes only GroovyDataTable and InfiniteScrollTable classes created by me.
Use it like this:
```groovy
infiniteTable('table') {
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