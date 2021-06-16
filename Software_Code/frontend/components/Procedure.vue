<template>
  <article v-bind:id="id" class="media result">
    <figure class="media-left">
      <p class="image is-100x100">
        <img src="https://bulma.io/images/placeholders/128x128.png" alt="image of Hospital">
      </p>
    </figure>
    <div class="media-content">
      <div class="content">
        <p class="subtitle is-5">{{hospitalName}}</p>

        <p><small>
          {{procedureName}}
        </small>
        </p>
        <p>
          Distance: {{distance}}
        </p>
        <p>
          $ {{price}}
        </p>
      </div>
    </div>
  </article>
</template>

<script>
  export default {
    props: {
      id:
        {
          type: String,
        },
      procedureName:
        {
          type: String,
        },
      hospitalName:
        {
          type: String,
        },
      address:
        {
          type: String,
        },
      price:
        {
          type: Number,
        },
      distance:
        {
          type: Number,
        },
      longitude:
        {
          type: Number,
        },
      latitude:
        {
          type: Number,
        },
    },
  };
</script>
<style scoped>

  .content p {
    margin-bottom: 8px;
  }

  .result {
    border: 1px lightgray solid;
    padding: 10px;
    border-radius: 3px;
  }

  article {
    margin-top: 1px;
    margin-bottom: 2px;
  }

</style>
