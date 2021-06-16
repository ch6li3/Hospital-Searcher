import {shallowMount} from '@vue/test-utils'
import Dropdown from '@/components/Dropdown.vue'

describe("Testing the Dropdown Component", () => {
  it("Procedure component is mounted correctly", () => {
    const wrapper = shallowMount(Dropdown,{
      stubs: {
        'b-field': true,
        'b-dropdown': true,
        'b-icon': true,
        'b-dropdown-item': true,
      }});
    expect(wrapper.isVueInstance()).toBeTruthy()
  });

  it("Dropdown is on low to high option", () => {
    const wrapper = shallowMount(Dropdown,{
      stubs: {
        'b-field': true,
        'b-dropdown': true,
        'b-icon': true,
        'b-dropdown-item': true,
      }});
    wrapper.setData({ selectedOption: 'Price: Low to High'});
    expect(wrapper.vm.selectedOption).toBe('Price: Low to High');
  });

  it("Dropdown is on high to low option", () => {
    const wrapper = shallowMount(Dropdown,{
      stubs: {
        'b-field': true,
        'b-dropdown': true,
        'b-icon': true,
        'b-dropdown-item': true,
      }});
    wrapper.setData({ selectedOption: 'High to Low'});
    expect(wrapper.vm.selectedOption).toBe('High to Low');
  });

  it("Dropdown is on high to low option", () => {
    const wrapper = shallowMount(Dropdown,{
      stubs: {
        'b-field': true,
        'b-dropdown': true,
        'b-icon': true,
        'b-dropdown-item': true,
      }});
    wrapper.setData({ selectedOption: 'Distance'});
    expect(wrapper.vm.selectedOption).toBe('Distance');
  });
});
