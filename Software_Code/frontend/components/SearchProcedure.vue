<template>
  <section class="section">
    <b-field class="searchInput-box" >
      <b-autocomplete
        class="autocomplete"
        :data="data"
        placeholder="Search Procedure by Name or Code"
        field="title"
        v-model="search"
        :loading="isFetching"
        @typing="getAsyncData"
        @select="searchFor">

        <template slot-scope="props">
          <div class="media-content">
            {{ props.option }}
          </div>
        </template>
      </b-autocomplete>
    </b-field>
  </section>
</template>

<script>
  import debounce from 'lodash/debounce';
  import axios from 'axios';

  export default {
    data() {
      return {
        search: '',
        data: [],
        selected: null,
        isFetching: false
      }
    },
    methods: {
      // You have to install and import debounce to use it,
      // it's not mandatory though.
      searchFor(value) {
        console.log(value);
      },

      getAsyncData: debounce(function (search) {
        if (!search.length) {
          this.data = [];
          return
        }
        this.isFetching = true;
        axios

          .get(`/api/autocomplete?a=${this.search}`)
          //local host version, uncomment above line of comment for the server version
          //.get('https://localhost:63362/autocomplete?a=${this.search}@userlat=30&userlng=-80&distance=${this.sliderNewDistance}&maxPrice=${this.slierNewPrice}')
          //.get('https://localhost:3000/autocomplete?a=${this.search}`)
          .then(({data}) => {
            this.data = [];
            console.log(data);
            data.forEach((item) => this.data.push(item))
          })
          .catch((error) => {
            this.data = [];
            throw error
          })
          .finally(() => {
            this.isFetching = false
          })
      }, 500)
    }
  }
</script>

<style scoped>
  .searchInput-box{
    display: inline-block;
    vertical-align: middle;
    width: 900px;
    padding: 2px;
    margin: 8px 0;
    box-sizing: border-box;
    border: #000066 ;
    border-radius: 4px;
    height: 38px;

  }
  .autocomplete
  {
    z-index: 10000;

  }
  .section
  {
    padding: 0;
    display: inline-block;
    vertical-align: middle;
  }
</style>
