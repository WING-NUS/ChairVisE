import {createLocalVue, shallowMount} from '@vue/test-utils'
import ElementUI from 'element-ui';
import VueRouter from 'vue-router'
import Vuex from 'vuex'
import App from '../App'

const localVue = createLocalVue();

localVue.use(Vuex);
localVue.use(VueRouter);
localVue.use(ElementUI);

describe('App.vue', () => {

  let store;

  beforeEach(() => {
    store = new Vuex.Store({
      state: {
        isPageLoading: false,
        userInfo: {
          isApiError: false
        },
        dbMetaData: {
          entitiesStatus: {
            isLoading: false
          }
        }
      }
    })
  });

  test('App is a vue instance and mounted correctly', () => {
    const wrapper = shallowMount(App, {store, localVue});
    expect(wrapper.isVueInstance()).toBeTruthy()
  })
});