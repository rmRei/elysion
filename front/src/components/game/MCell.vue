<template>
  <div class="mcell" :class="{ opened: cell.isOpened }">
    {{ isLost && cell.hasBomb ? bomb : cell.contents }}
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "MCell",
  props: {
    isLost: Boolean,
    bomb: String,
    cell: Object
  }
});
</script>

<style>
.mcell {
  color: #000000;
  font-weight: bold;
  display: flex;
  justify-content: center;
  align-items: center;
  box-sizing: border-box;
  background-color: #ffffff;
  border: solid #000000 1px;
}
.mcell:hover {
  background-color: #999999;
  border: solid #999999 1px;
}
.opened {
  background-color: #cccccc;
}
</style>
