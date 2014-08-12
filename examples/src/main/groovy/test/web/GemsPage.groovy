package test.web

class GemsPage extends TemplatePage {
    @Override
    protected void onInitialize() {
        super.onInitialize()
        infiniteTable('table') {
            cell('id')
            cell('name')
            cell('surname')
            list { offset ->
                Person.generate(offset)
            }
        }
    }

    static class Person implements Serializable {
        Long id
        String name
        String surname

        static List<Person> generate(long offset) {
            def list = []
            40.times {
                def id = (offset + 1) * it
                list << new Person(id: id, name: "Gomer $id", surname: "Simpson $id")
            }
            list
        }
    }
}
