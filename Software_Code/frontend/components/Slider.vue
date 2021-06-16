<!-- Sources used: https://buefy.org/documentation/slider-->
<template>
  <section class="section">
    <b-field class="group">
      <b-field horizontal label="Distance:">
        <b-input class="d-input" v-model="distance" type="number"></b-input>
      </b-field>
      <b-field horizontal label="Price:">
        <b-input class="p-input" v-model="price" type="number"></b-input>
      </b-field>
    </b-field>
    <b-field grouped>
    <b-field>
      <b-slider class="d-slider" v-model="distance" lazy></b-slider>
    </b-field>
    <b-field>
      <b-slider class="p-slider" v-model="price" lazy></b-slider>
    </b-field>
    </b-field>
  </section>
</template>

<script>
  export default {
    data() {
      return {
        price: 30,
        distance: 50
      }
    }
  }
</script>
<style>
  .section
  {
    padding: 15px 5px 0 5px;
    margin-bottom: auto;
  }
  .d-input
  {
    width: 105px;
    margin-right: 45px;
  }
  .p-input
  {
    width: 105px;
  }
  .d-slider
  {
    width: 200px;
    margin-right: 30px
  }
  .p-slider
  {
    width: 200px;
  }
</style>
