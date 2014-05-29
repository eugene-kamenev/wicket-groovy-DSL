package test.web

class HomePage extends TemplatePage {
    @Override
    protected void onInitialize() {
        super.onInitialize()
        this.add(personListFragment())
    }
}
