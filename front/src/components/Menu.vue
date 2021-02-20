<template>
  <a
    :href="menu.href"
    class="button menu"
    v-for="menu in menuList"
    :key="menu.text"
  >
    <img
      class="menu"
      :src="[menu.image == '' ? '/images/hand.png' : menu.image]"
    />{{ menu.text }}
  </a>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import axios from "axios";

export default defineComponent({
  name: "Menu",
  data() {
    return {
      menuList: []
    };
  },
  created() {
    axios
      .get("/master/MenuList.json")
      .then(response => (this.menuList = response.data))
      .catch(error => console.log(error));
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
  margin-left: -50px;
  margin-right: 25px;
}
</style>
