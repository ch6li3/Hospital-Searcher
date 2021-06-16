<template>
  <div>
    <header class="header">
  <div class="columns is-vcentered">
    <div class="column is-4">
      <img src="~assets/medic.png"
           height="50"
           width="45"
           align="left"
           alt="Medicine snake logo"
      />
      <h1 style="vertical-align: middle; display: inline-block">CraneWare</h1>
    </div>
    <div class="column is-8">
      <SearchProcedure :results.sync="results"></SearchProcedure>
      <button id="locate" class="button" v-on:click="getLocation">
        <span class="icon is-small">
          <font-awesome-icon :icon="['fas', 'location-arrow']"/>
        </span>
      </button>
    </div>
  </div>



    </header>

    <div id="main" class="columns">


      <div id="results" class="column is-4">
        <div class="filters">
          <Dropdown></Dropdown>
          <Slider></Slider>
        </div>
        <div id="resultList">
          <div class="resultbox" v-for="(result, index) in results" @click="selectedResult(index)"
               :class="{active:index == activeBox}">
            <Procedure :id="'result'+index"
                       :hospital-name="result.hospitalName"
                       :procedureName="result.procedureName"
                       :distance="result.distance"
                       :price="result.price"></Procedure>
          </div>
          <div class="noResults" v-if="results.length ===0">
            <div class="columns is-vcentered">
              <div class="column is-2">
                <font-awesome-icon :icon="['fas', 'info']" full-width class="fa-5x"/>
              </div>
              <div class="column is-10">
                <h2>Your results will be shown here. Please use the search bar above!</h2>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div id="map" class="column is-8">
        <div id="map-wrap" style="height: 100%">
          <no-ssr>
            <l-map ref="myMap"
                   :zoom="zoom"
                   :center="center">
              <l-tile-layer url="https://{s}.tile.osm.org/{z}/{x}/{y}.png"></l-tile-layer>
              <l-marker v-if="userLocation.lat != null"
                        :lat-lng="[userLocation.lat, userLocation.lng]">
                <l-icon>
                  <div class="userlocation"></div>
                </l-icon>
                <l-popup><p>This is your current location</p></l-popup>
              </l-marker>
              <v-marker-cluster>
                <div v-for="(item, index) in results">
                  <l-marker v-on:click="scroll(index)" :lat-lng="[item.latitude, item.longitude ]">
                    <l-icon v-on:click="scroll(index)">

                      <div class="hospitalLocation">
                        ${{item.price}}
                      </div>
                    </l-icon>
                  </l-marker>
                </div>
              </v-marker-cluster>
            </l-map>
          </no-ssr>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Dropdown from "../components/Dropdown.vue";
  import Slider from "../components/Slider";
  import Procedure from "../components/Procedure";
  import SearchProcedure from "../components/SearchProcedure";

  export default {
    name: 'MediPage',


    components: {
      Procedure,
      Slider,
      Dropdown,
      SearchProcedure,
    },
    data() {
      return {
        results: [],
        zoom: 6,
        activeBox: null,
        center: [47.413220, -1.219482],
        bounds: null,
        userLocation: {lat: null, lng: null}
      };
    },
    methods: {
      scroll(index) {
        if (this.activeBox === index) return;
        this.activeBox = index;
        const options = {
          container: '#resultList',
          easing: 'ease-in',
          offset: -60,
          y: true
        };
        this.$scrollTo('#result' + index, 200, options);

      },
      selectedResult(index) {
        if (this.activeBox === index) return;
        this.activeBox = index;
        this.$nextTick(() => {
          this.$refs.myMap.mapObject.flyTo([this.results[index].latitude, this.results[index].longitude], 15);
        });
      },
      getLocation() {
        // do we support geolocation
        if (!('geolocation' in navigator)) {
          console.log('Geolocation is not available.');
          return;
        }
        // get position
        navigator.geolocation.getCurrentPosition(pos => {
          this.$nextTick(() => {
            this.userLocation.lng = pos.coords.longitude;
            this.userLocation.lat = pos.coords.latitude;
            this.$refs.myMap.mapObject.flyTo([pos.coords.latitude, pos.coords.longitude], 15);
          });
        }, err => {
          console.log('Not allowed to get position :/')
        })
      },
    }
  }
</script>

<style>
  html {
    overflow: auto;
  }

  body {
    font-family: Arial, Helvetica, sans-serif;
    line-height: 1.4;
    overflow: hidden;
  }

  #main {
    height: calc(100vh - 88px);
    margin: 0;
  }

  #map {
    background: #DDDDDD;
    padding: 0;
  }

  #results {
    background: ghostwhite;
    overflow-y: hidden;
    height: calc(100vh - 88px);
    border-right: 1px solid lightgray;
    padding: 0;
  }

  #resultList {
    overflow-y: scroll;
    height: calc(100vh - 343px);
    border-top: 1px solid lightgrey;
    padding: 2px 0.75rem 0.75rem;
  }

  .filters {
    padding: 0.75rem 0.75rem 0;
  }

  .header {
    display: inline-block;
    vertical-align: middle;
    background: #1abc9c;
    color: #ffffff;
    font-size: 30px;
    font-family: 'Alegreya Sans SC', serif;
    text-align: left;
    padding: 15px;
    display: flex;
    border-bottom: 1px solid lightgray;
  }

  .userlocation {
    margin-left: -5px;
    background: #7957d5;
    width: 20px;
    height: 20px;
    border-radius: 20px
  }

  #locate {
    display: inline-block;
    vertical-align: middle;
    height: 38px;
  }

  .hospitalLocation {
    background: #7957d5;
    width: min-content;
    height: 25px;
    border: 2px solid black;
    border-radius: 18px;
    padding: 0px 8px;
    color: white;
    font-weight: bolder;
    font-size: 15px;
  }

  .resultbox {
    margin-bottom: 10px;
  }

  .resultbox.active {
    background: lightblue;
    box-shadow: 2px 2px 4px 5px #ccc;
  }

  .noResults {
    padding: 200px 20px;
  }

  .button
  {
    margin-left: 0;
    margin-top: 0;
    display: inline-block;
    vertical-align: middle;
  }

  .columns
  {
    width: 100%;
  }
  .column is-8
  {
    width: 100%;
    display: inline-block;
    vertical-align: middle;
  }
</style>
