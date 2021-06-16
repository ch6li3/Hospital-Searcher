import {shallowMount} from '@vue/test-utils'
import Slider from '@/components/Slider.vue'

describe("Testing for Slider Component", () => {
  it("Slider component is mounted correctly", () => {
    const wrapper = shallowMount(Slider,{
      stubs: {
        'b-field': true,
        'b-slider': true,
      }});
    expect(wrapper.isVueInstance()).toBeTruthy()
  });
  it("Slider is at 20 miles for distance", () => {
    const wrapper = shallowMount(Slider,{
      stubs: {
        'b-field': true,
        'b-slider': true,
      }});
    wrapper.setData({ distance: 20});
    expect(wrapper.vm.distance).toEqual(20);
  });
  it("Slider is at $30 for price", () => {
    const wrapper = shallowMount(Slider,{
      stubs: {
        'b-field': true,
        'b-slider': true,
      }});
    wrapper.setData({ price: 30})
    expect(wrapper.vm.price).toEqual(30);
  });

});
