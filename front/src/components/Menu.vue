<template>
  <a :href="menu.href" class="button menu" v-for="menu in menuList" :key="menu">
    <img
      class="menu"
      :src="[menu.image == '' ? '/images/hand.png' : menu.image]"
    />{{ menu.text }}
  </a>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import axios from "axios";

export default defineComponent({
  name: "Menu",
  setup() {
    const menuList = ref([]);

    axios
      .get("/master/MenuList.json")
      .then(response => (menuList.value = response.data))
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
  height: 50px;
  line-height: 50px;
}
img.menu {
  width: 15px;
  height: 15px;
  margin-left: 30px;
  margin-right: 30px;
}
</style>
