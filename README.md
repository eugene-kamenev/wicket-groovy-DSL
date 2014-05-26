#Apache Wicket Groovy DSL Project

## Overview
Apache Wicket is great, and Groovy is also great. This project tries to combine the power of both. However, sometimes Apache Wicket code become damn verbose.
But with some little, yet powerful Groovy DSL written, we can extend Wicket to simplify common tasks, and to delete over 30-40% of verbose code.
Versions used:
### 1. Apache Wicket 6.15.0
### 2. Groovy 2.3.1 (Yes, with Traits :)
### 3. Twitter bootstap for some beautify

## Examples

Later I will provide more complex examples. But to understand the concept, I think it is enough

##Usage
```groovy
...
use(WicketDSL) {
    def form('wicketForm', new CompoundPropertyModel(this), [submit: { println this.input1 + this.input2 }, visible: {this.input1 != this.input2} ])
    form.field('input1')
    form.field('input2')
    this << form
}
```
Instead of
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
   }
   form.add(new TextField("input1"));
   form.add(new TextField("input2"));
   add(form);
```

And this is just a small example of usage. Also with an override map (in this example with 'submit' key), you can pass another properties to components, like
visible, enabled and others.

##Thanks

Thanks to great Groovy Community, and especially to Andrey Bloschetsov

##Development
DSL is not complete, it is just the attempt to show you the concept.
Feel free to contact me, to advise me how to make this DSL better.

##Help
I need help in testing this DSL, also I need your opinion of this