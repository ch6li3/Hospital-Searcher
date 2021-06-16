<!--Sources used https://buefy.org/documentation/dropdown/-->
<template>
  <section class="section">
    <b-field class="dropdown" horizontal label="Sort By:">
      <b-dropdown v-model="selectedOption" aria-role="list">
        <button class="button is-primary" type="button" slot="trigger">
          <template>
            <span style="display: inline-block" >{{ selectedOption }}</span>
            <b-icon icon="menu-down"></b-icon>
          </template>
        </button>

        <b-dropdown-item value="Price: Low to High" aria-role="listitem">
          <span>Price: Low to High</span>
        </b-dropdown-item>

        <b-dropdown-item value="Price: High to Low" aria-role="listitem">
          <span>Price: High to Low</span>
        </b-dropdown-item>

        <b-dropdown-item value="Distance" aria-role="listitem">
          <span>Distance</span>
        </b-dropdown-item>
      </b-dropdown>
    </b-field>
  </section>
</template>

<script>
  export default {
    data() {
      return {
        selectedOption: "Price: Low to High"
      }
    }
  }
</script>

<style scoped>
  .dropdown
  {
    margin-bottom: auto;
    width: 100%;
  }
  .section {
    margin-left: 70px;
  }
</style>
