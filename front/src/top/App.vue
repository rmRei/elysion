<template>
  <div class="sidemenu">
    <Menu />
  </div>
  <div class="contents"></div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import Menu from "../components/Menu.vue";

export default defineComponent({
  name: "App",
  components: {
    Menu
  }
});
</script>

<style>
#app {
  display: flex;
  overflow: hidden;
}
.sidemenu {
  width: 20%;
  height: 100vh;
  background-color: #111111;
}
.sidemenu a.menu {
  height: 5vh;
  line-height: 5vh;
}
.contents {
  width: 80%;
}
</style>
