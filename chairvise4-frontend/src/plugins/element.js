import Vue from 'vue'
import {
  Button, Message, Container, Header, Main, Menu, MenuItem, Dialog, Input, DatePicker, Switch,
  Submenu, Row, Col, Table, TableColumn, Form, FormItem, Tag, RadioGroup, RadioButton
} from 'element-ui'
import lang from 'element-ui/lib/locale/lang/en'
import locale from 'element-ui/lib/locale'

locale.use(lang)

Vue.use(Button)
Vue.use(Container)
Vue.use(Header)
Vue.use(Main)
Vue.use(MenuItem)
Vue.use(Menu)
Vue.use(Submenu)
Vue.use(Row)
Vue.use(Col)
Vue.use(Table)
Vue.use(TableColumn)
Vue.use(Form)
Vue.use(FormItem)
Vue.use(Tag)
Vue.use(Dialog)
Vue.use(Switch)
Vue.use(Input)
Vue.use(DatePicker)
Vue.use(RadioGroup)
Vue.use(RadioButton)

Vue.prototype.$message = Message
