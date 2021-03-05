<template>
  <div class="cell" :class="{ opened: cell.opened }">
    {{ isLose && cell.hasBomb ? bomb : cell.contents }}
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "MCell",
  props: {
    isLose: Boolean,
    bomb: String,
    cell: Object
  }
});
</script>

<style>
.cell {
  color: #000000;
  font-weight: bold;
  display: flex;
  justify-content: center;
  align-items: center;
  box-sizing: border-box;
  background-color: #ffffff;
  border: solid #000000 1px;
}
.cell:hover {
  background-color: #999999;
  border: solid #999999 1px;
}
.opened {
  background-color: #cccccc;
}
</style>
