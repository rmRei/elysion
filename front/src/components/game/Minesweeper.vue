<template>
  <div class="minesweeper">
    <span class="bombcount">{{ BOMB + "：" + (bombsize - flagsize) }}</span>
    <span v-on:click="init">Elysion</span>
    <img
      v-on:click="toggleHidden"
      class="config"
      :src="[isShow ? '/images/cross.png' : '/images/gear.png']"
    />
    <Timer ref="timer" />
    <div :class="level" v-show="!isShowConfig">
      <div v-for="row in map" :key="row" class="row">
        <MCell
          :isLose="isLose"
          :bomb="BOMB"
          :cell="cell"
          v-for="cell in row"
          :key="cell"
          v-on:click="open(cell)"
          v-on:click.right.prevent="check(cell)"
        />
      </div>
    </div>
    <div v-show="isShowConfig" class="selectWindow">
      <SelectButton v-on:result="selectLevel" :optionList="levelList" />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from "vue";
import MCell from "./MCell.vue";
import Timer from "../Timer.vue";
import SelectButton from "../SelectButton.vue";

const FLAG = "🚩";
const BOMB = "💣";

class Cell {
  x: number;
  y: number;
  hasBomb: boolean;
  opened: boolean;
  contents: string;
  constructor(x: number, y: number) {
    this.x = x;
    this.y = y;
    this.hasBomb = false;
    this.opened = false;
    this.contents = "";
  }
}

export default defineComponent({
  name: "Minesweeper",
  components: {
    MCell,
    Timer,
    SelectButton
  },
  setup() {
    let isStarted = false;
    let mapsize = 0;
    let openedsize = 0;
    let isCompleted = false;
    const isShowConfig = ref(false);
    const levelList = ref(["easy", "normal", "hard"]);
    const level = ref(levelList.value[0]);
    const isLose = ref(false);
    const xsize = ref(10);
    const ysize = ref(10);
    const bombsize = ref(10);
    const flagsize = ref(0);
    const map = ref();

    const timer = ref();

    const createMap = () => {
      const noBombField = [];
      for (let y = 0; y < ysize.value; y++) {
        const row = [];
        for (let x = 0; x < xsize.value; x++) {
          row.push(new Cell(x, y));
          noBombField.push({ x, y });
        }
        map.value.push(row);
      }
      mapsize = noBombField.length;
      for (let bomb = 0; bomb < bombsize.value; bomb++) {
        const index = Math.floor(Math.random() * noBombField.length);
        const field = noBombField[index];
        noBombField.splice(index, 1);
        map.value[field.y][field.x].hasBomb = true;
      }
    };

    const init = (level: string) => {
      timer.value.stop();
      timer.value.reset();
      map.value = [];
      flagsize.value = 0;
      isLose.value = false;
      isStarted = false;
      openedsize = 0;
      isCompleted = false;
      if (level == "easy") {
        xsize.value = 10;
        ysize.value = 10;
        bombsize.value = 10;
      } else if (level == "normal") {
        xsize.value = 20;
        ysize.value = 20;
        bombsize.value = 80;
      } else if (level == "hard") {
        xsize.value = 30;
        ysize.value = 30;
        bombsize.value = 225;
      }
      createMap();
    };

    const toggleHidden = () => {
      isShowConfig.value = !isShowConfig.value;
    };

    const selectLevel = (result: string) => {
      level.value = result;
      toggleHidden();
      timer.value.stop();
      timer.value.reset();
      init(level.value);
    };

    const getNeighbours = (cell: Cell) => {
      const list = [];
      const minX = Math.max(0, cell.x - 1);
      const maxX = Math.min(xsize.value - 1, cell.x + 1);
      const minY = Math.max(0, cell.y - 1);
      const maxY = Math.min(ysize.value - 1, cell.y + 1);
      for (let x = minX; x <= maxX; x++) {
        for (let y = minY; y <= maxY; y++) {
          if (x != cell.x || y != cell.y) {
            list.push(map.value[y][x]);
          }
        }
      }
      return list;
    };

    const open = (cell: Cell) => {
      if (!isStarted && cell.hasBomb) {
        init(level.value);
        open(map.value[cell.y][cell.x]);

        return;
      }

      if (cell.opened || cell.contents == FLAG || isLose.value || isCompleted) {
        return;
      }

      if (cell.hasBomb) {
        isLose.value = true;
        timer.value.stop();
        return;
      } else {
        const neighbours = getNeighbours(cell);
        let neighboursBombsize = 0;
        neighbours.forEach((neighbour: Cell) => {
          if (neighbour.hasBomb) {
            neighboursBombsize++;
          }
        });
        if (!isStarted && neighboursBombsize != 0) {
          init(level.value);
          open(map.value[cell.y][cell.x]);

          return;
        }

        cell.opened = true;
        openedsize++;

        if (!isStarted) {
          timer.value.start();
          isStarted = true;
        }

        if (neighboursBombsize == 0) {
          neighbours.forEach((neighbour: Cell) => {
            if (!neighbour.opened && neighbour.contents != FLAG) {
              open(neighbour);
            }
          });
        } else {
          cell.contents = neighboursBombsize.toString();
        }
        if (mapsize == openedsize + bombsize.value && !isCompleted) {
          timer.value.stop();
          isCompleted = true;
          alert(
            "クリアおめでとう\r\nタイム：" +
              Math.floor(timer.value.timer / 60) +
              "分" +
              (timer.value.timer % 60) +
              "秒"
          );
        }
      }
    };

    const check = (cell: Cell) => {
      if (cell.opened || isLose.value) {
        return;
      }

      if (cell.contents == FLAG) {
        cell.contents = "";
        flagsize.value--;
      } else {
        cell.contents = FLAG;
        flagsize.value++;
      }
    };

    onMounted(() => {
      init(level.value);
    });

    return {
      BOMB,
      isShowConfig,
      levelList,
      level,
      isLose,
      xsize,
      ysize,
      bombsize,
      flagsize,
      map,
      timer,
      init,
      toggleHidden,
      selectLevel,
      open,
      check
    };
  }
});
</script>

<style>
.minesweeper {
  position: relative;
  max-width: 90vmin;
  width: 80%;
  height: fit-content;
  border: solid;
  top: 0.5em;
  margin: auto;
  text-align: center;
}
.bombcount {
  position: absolute;
  top: 10px;
  left: 10px;
}
.config {
  position: absolute;
  top: 10px;
  right: 10px;
  height: 30px;
}
.selectWindow > button {
  width: 30%;
  margin: 1%;
  font-size: 1em;
}
.row:before {
  content: "";
}
.row {
  display: flex;
}
.easy .cell {
  width: 10%;
  font-size: 4vmin;
}
.normal .cell {
  width: 5%;
  font-size: 2vmin;
}
.hard .cell {
  width: 3.333%;
  font-size: 1.5vmin;
}
.easy .row:before {
  padding-top: 10%;
}
.normal .row:before {
  padding-top: 5%;
}
.hard .row:before {
  padding-top: 3.333%;
}
</style>
