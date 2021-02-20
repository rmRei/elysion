<template>
  <div class="sidemenu">
    <Menu />
    <div v-on:click="toggleHidden"></div>
  </div>
  <div class="contents">
    <Chat v-if="isHidden" placeholder="入力欄" />
    <Chat v-else placeholder="隠し部屋e" />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import Menu from "../components/Menu.vue";
import Chat from "../components/Chat.vue";

export default defineComponent({
  name: "App",
  components: {
    Menu,
    Chat
  },
  data() {
    return {
      isHidden: true
    };
  },
  methods: {
    toggleHidden() {
      this.isHidden = !this.isHidden;
    }
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
