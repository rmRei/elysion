<template
  ><button class="clear" v-on:click="clearText">クリア</button>
  <textarea rows="15" :placeholder="placeholder" v-model="text"></textarea>
  <div class="textarea">{{ text }}</div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "Chat",
  props: {
    placeholder: String
  },
  data() {
    return {
      text: ""
    };
  },
  methods: {
    clearText() {
      this.text = "";
    }
  }
});
</script>

<style>
.textarea {
  white-space: pre-wrap;
  overflow-x: auto;
  height: 68vh;
}
textarea {
  width: 100%;
  box-sizing: border-box;
  background-color: #000000;
  color: #ffffff;
}
.clear {
  position: absolute;
  bottom: 0px;
  left: 0;
  width: 20vw;
  height: 50px;
  font-size: large;
}
</style>
