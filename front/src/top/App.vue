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
  width: auto;
  height: 100vh;
  display: flex;
}
.sidemenu {
  width: 20%;
  height: 100vh;
  background-color: #111111;
}
.contents {
  width: 80%;
  overflow: hidden;
}
</style>
