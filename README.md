#Apache Wicket Groovy DSL Project 0.2

## Overview
Apache Wicket is great, and Groovy is also great. This project tries to combine the power of both. However, sometimes Apache Wicket code become damn verbose.
But with some little, yet powerful Groovy DSL written, we can extend Wicket to simplify common tasks, and to delete over 30-40% of verbose code.
Versions used:
#### 1. Apache Wicket 6.16.0
#### 2. Groovy 2.3.4 (Yes, with Traits :)
#### 3. Twitter bootstap for some beautify for examples

## Changes from previous version
Previous version was not well, DSL syntax was not so good, and the main problem was that we cannot use @CompileStatic groovy compiler annotation.
Now things had changed. Take a look at v1 branch readme.md if you do not understand the code, there are some java/groovy compared examples.
In core package inside META-INF/services you can find org.codehaus.groovy.runtime.ExtensionModule file, that tells that we are adding WicketDSL globally in the project.
So every first method parameter in WicketDSL class will be forced to component that it was called from. For example:

##Simple Usage
```groovy
...
@Override
void onInitialize() {
form('wicketForm') {
    compoundModel(this)
    text 'input1'
    text 'input2'
    number('someNumberFieldInClass').model(property('someField'))
    visible {
        this.input1 != this.input2
    }
    submit {
        println this.input1 + this.input2
    }
}
```

Now we can chain components, one inside another, with groovy you can become lazy and totally forget about component.add(another)
AjaxLink with Label:
```groovy
...
ajaxLink('link') {
   label('label') {
   ...
   }
   click { AjaxRequestTarget t ->

   }
 }
}
```
Here AjaxLink is passed into its closure transparently, than label added to it. I have included groovy annotations @DelegatesTo and @ClosureParams on method parameters,
so your ide will now help you much with code completion.
Every object can be converted into wicket's LoadableDetachableModel simply:
```groovy
...
ajaxLink('link') {
   label('label').model(5.toLoadModel())
   label('label2').model('some string'.toLoadModel())
   compoundModel(this) // set AjaxLink model to CompoundPropertyModel
   label('label3') {
     model(this.property('someProperty')) // set Label model to PropertyModel(this, 'someProperty')
   }
 }
}.click {
    // click can be written here, not only inside ajaxLink
}.visible {
    // and than chained with another method
}.model {
    // you can set model as closure also
}.css {
    // and simply add css classes to markup as conditional closures
}
```
And much more.
Closure delegates to delegate component first, in our case delegate is AjaxLink, and closure delegates to it, and yes, all standart wicket component methods are accessible inside closures.
For now I have not enough time to describe all features I had included inside. You can browse yourself.

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