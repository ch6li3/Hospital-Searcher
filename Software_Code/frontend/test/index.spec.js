import {shallowMount} from "@vue/test-utils"
import Index from "@/pages/index.vue"

describe("Index Navbar", () => {
  it("Navbar should be visible", () => {
    const wrapper = shallowMount(Index, {
      stubs: {
        'font-awesome-icon': true,
        'no-ssr': true,
        'l-map': true,
        'l-tile-layer': true,
        'l-marker': true,
      }
    });
    expect(wrapper.find("header").isVisible()).toBe(true)
  });
  it("Navbar should have a logo", () => {
    const wrapper = shallowMount(Index, {
      stubs: {
        'font-awesome-icon': true,
        'no-ssr': true,
        'l-map': true,
        'l-tile-layer': true,
        'l-marker': true,
      }
    });
    expect(wrapper.find("header img").isVisible()).toBe(true)
  });
  it("Navbar should have a title", () => {
    const wrapper = shallowMount(Index, {
      stubs: {
        'font-awesome-icon': true,
        'no-ssr': true,
        'l-map': true,
        'l-tile-layer': true,
        'l-marker': true,
      }
    });
    expect(wrapper.find("header h1").text()).toBe("CraneWare")
  });
});

describe("Index Main Body", () => {
  it("Main body should have a main section", () => {
    const wrapper = shallowMount(Index, {
      stubs: {
        'font-awesome-icon': true,
        'no-ssr': true,
        'l-map': true,
        'l-tile-layer': true,
        'l-marker': true,
      }
    });
    expect(wrapper.find("#main").isVisible()).toBe(true)
  });
  it("Body should have a result list", () => {
    const wrapper = shallowMount(Index, {
      stubs: {
        'font-awesome-icon': true,
        'no-ssr': true,
        'l-map': true,
        'l-tile-layer': true,
        'l-marker': true,
      }
    });
    expect(wrapper.find("#results").isVisible()).toBe(true)
  });
  it("Body should have a map", () => {
    const wrapper = shallowMount(Index, {
      stubs: {
        'font-awesome-icon': true,
        'no-ssr': true,
        'l-map': true,
        'l-tile-layer': true,
        'l-marker': true,
      }
    });
    expect(wrapper.find("#map").isVisible()).toBe(true)
  });
  it("Map should be loaded correctly", () => {
    const wrapper = shallowMount(Index, {
      stubs: {
        'font-awesome-icon': true,
        'no-ssr': true,
        'l-tile-layer': true,
        'l-marker': true,
      }
    });
    expect(wrapper.find("l-map").isVisible()).toBe(true)
  });
  it("Body should have a map", () => {
    const wrapper = shallowMount(Index, {
      stubs: {
        'font-awesome-icon': true,
        'no-ssr': true,
        'l-map': true,
        'l-tile-layer': true,
        'l-marker': true,
      }
    });
    expect(wrapper.find("#map").isVisible()).toBe(true)
  });

});

it("There should be a geolocation button", () => {
  const wrapper = shallowMount(Index, {
    stubs: {
      'font-awesome-icon': true,
      'no-ssr': true,
      'l-map': true,
      'l-tile-layer': true,
      'l-marker': true,
    }
  });
  expect(wrapper.find("#locate").isVisible()).toBe(true)
});

it("Geolocation data should be saved in userLocation", () => {
  const wrapper = shallowMount(Index, {
    stubs: {
      'font-awesome-icon': true,
      'no-ssr': true,
      'l-map': true,
      'l-tile-layer': true,
      'l-marker': true,
      'l-icon': true,
      'l-popup': true,
    }
  });

  wrapper.vm.$refs.myMap.mapObject = {
    flyTo: jest.fn(),
  };

  global.navigator.geolocation = {
    getCurrentPosition: jest.fn()
      .mockImplementationOnce((success) => Promise.resolve(success({
        coords: {
          latitude: 51.1,
          longitude: 45.3
        }
      })))
  };

  wrapper.vm.getLocation();

  wrapper.vm.$nextTick(() => {
    expect(wrapper.vm.userLocation.lat).toBe(51.1);
    expect(wrapper.vm.userLocation.lng).toBe(45.3);
    done()
  })
});
