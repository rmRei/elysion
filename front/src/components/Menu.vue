<template>
  <a class="button menu" :href="menu.href" v-for="menu in menuList" :key="menu">
    <img class="menu" :src="menu.image" /><span>{{ menu.text }}</span>
  </a>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import axios from "axios";

export default defineComponent({
  name: "Menu",
  setup() {
    const menuList = ref();

    axios
      .get("/master/MenuList.json")
      .then(response => {
        menuList.value = response.data;
      })
      .catch(error => console.log(error));

    return {
      menuList
    };
  }
});
</script>

<style>
a.menu {
  font-size: 20px;
}
img.menu {
  width: 15px;
  margin-left: 30px;
  margin-right: 30px;
}
</style>
