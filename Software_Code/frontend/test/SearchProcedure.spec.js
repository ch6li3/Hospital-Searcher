import {shallowMount} from '@vue/test-utils'
import SearchProcedure from '@/components/SearchProcedure.vue'

describe("Testing the SearchProcedure Component", () => {
  it("Test SearchProcedure component is mounted correctly", () => {
    const wrapper = shallowMount(SearchProcedure);
    expect(wrapper.isVueInstance()).toBeTruthy()
  });

  it("Testing entered triggers submit event", () => {
    const wrapper = shallowMount(SearchProcedure);
    wrapper.vm.quantity = 13; //ENTER key code
    wrapper.trigger('keyup');
    expect(wrapper.vm.quantity).toBe(13)
  });
});
