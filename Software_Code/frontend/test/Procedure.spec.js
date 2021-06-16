import {shallowMount} from '@vue/test-utils'
import Procedure from '@/components/Procedure.vue'

describe("Testing the Procedure Component", () => {
  it("Procedure component is mounted correctly", () => {
    const wrapper = shallowMount(Procedure);
    expect(wrapper.isVueInstance()).toBeTruthy()
  });

  it("Test if Component is a valid selector", () => {
    const wrapper = shallowMount(Procedure);
    expect(wrapper.is(Procedure)).toBe(true)
  });
  it("Test if Component props objects will be returned", () => {
    const wrapper = shallowMount(Procedure, {
      propsData: {
        procedureName: '023 - CRANIOTOMY W MAJOR DEVICE IMPLANT OR ACUTE CNS PDX W MCC OR CHEMOTHE',
        hospitalName: 'SOUTHEAST ALABAMA MEDICAL CENTER',
        address: '1108 ROSS CLARK CIRCLE DOTHAN 36301',
        price: 117117.7857,
        longitude: -77.0364,
        latitude: 38.8951,
        distance:128

      }
    });
    expect(wrapper.props().procedureName).toBe('023 - CRANIOTOMY W MAJOR DEVICE IMPLANT OR ACUTE CNS PDX W MCC OR CHEMOTHE');
    expect(wrapper.props('procedureName')).toBe('023 - CRANIOTOMY W MAJOR DEVICE IMPLANT OR ACUTE CNS PDX W MCC OR CHEMOTHE');
    expect(wrapper.props().hospitalName).toBe('SOUTHEAST ALABAMA MEDICAL CENTER');
    expect(wrapper.props('hospitalName')).toBe('SOUTHEAST ALABAMA MEDICAL CENTER');
    expect(wrapper.props().address).toBe('1108 ROSS CLARK CIRCLE DOTHAN 36301');
    expect(wrapper.props('address')).toBe('1108 ROSS CLARK CIRCLE DOTHAN 36301');
    expect(wrapper.props().price).toBe(117117.7857);
    expect(wrapper.props('price')).toBe(117117.7857);
    expect(wrapper.props().longitude).toBe(-77.0364);
    expect(wrapper.props('longitude')).toBe(-77.0364);
    expect(wrapper.props().latitude).toBe(38.8951);
    expect(wrapper.props('latitude')).toBe(38.8951)
  });
  it("Test if Component setData works ", () => {
    const wrapper = shallowMount(Procedure);
    wrapper.setData({procedureName: '033 bar'});
    expect(wrapper.vm.procedureName).toBe('033 bar')
  });
  it("Test if Component prop types for String ", () => {
    const wrapper = shallowMount(Procedure);
    wrapper.setData({procedureName: 11});
    expect(wrapper.find('.error').exists()).toBeFalsy()
  });
  it("Test if Component prop type for Numbers", () => {
    const wrapper = shallowMount(Procedure);
    wrapper.setData({price: '033 bar'});
    expect(wrapper.find('.error').exists()).toBeFalsy()
  });
});
