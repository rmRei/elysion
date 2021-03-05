<template>
  <div class="sidemenu">
    <Menu />
  </div>
  <div class="contents">
    <Minesweeper />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import Menu from "../components/Menu.vue";
import Minesweeper from "../components/game/Minesweeper.vue";

export default defineComponent({
  name: "App",
  components: {
    Menu,
    Minesweeper
  }
});
</script>

<style>
#app {
  width: auto;
  height: 100vh;
  display: flex;
  overflow: hidden;
  font-size: x-large;
}
.sidemenu {
  width: 20%;
  height: 100vh;
  background-color: #111111;
}
.contents {
  width: 80%;
}
</style>
