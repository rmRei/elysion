<template>
  <div class="minesweeper center">
    <div class="header">
      <span class="left">{{ BOMB + "：" + (bombsize - flagCount) }}</span>
      <div class="center"><Timer ref="timer" /></div>
      <div class="config">
        <img src="/images/reload.png" v-if="isStarted" v-on:click="init" />
        <img
          :src="[shownConfig ? '/images/cross.png' : '/images/gear.png']"
          v-on:click="toggleShownConfig"
        />
      </div>
    </div>
    <div :class="level" v-show="!shownConfig">
      <div class="row" v-for="row in map" :key="row">
        <MCell
          :isLost="isLost"
          :bomb="BOMB"
          v-for="cell in row"
          :key="cell"
          :cell="cell"
          v-on:click="open(cell)"
          v-on:click.right.prevent="check(cell)"
        />
      </div>
    </div>
    <div class="selectlevel" v-show="shownConfig">
      <SelectButton v-on:result="selectLevel" :optionList="LEVEL_LIST" />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from "vue";
import MCell from "./MCell.vue";
import Timer from "../Timer.vue";
import SelectButton from "../SelectButton.vue";

const LEVEL_LIST = ["easy", "normal", "hard"];
const FLAG = "🚩";
const BOMB = "💣";

class Cell {
  x: number;
  y: number;
  hasBomb: boolean;
  isOpened: boolean;
  contents: string;
  constructor(x: number, y: number) {
    this.x = x;
    this.y = y;
    this.hasBomb = false;
    this.isOpened = false;
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
    let openedCount = 0;
    let isCompleted = false;
    let xsize = 10;
    let ysize = 10;
    const shownConfig = ref(false);
    const level = ref(LEVEL_LIST[0]);
    const isStarted = ref(false);
    const isLost = ref(false);
    const bombsize = ref(10);
    const flagCount = ref(0);
    const map = ref();

    const timer = ref();

    const createMap = () => {
      const noBombMap = [];
      for (let y = 0; y < ysize; y++) {
        const row = [];
        for (let x = 0; x < xsize; x++) {
          row.push(new Cell(x, y));
          noBombMap.push({ x, y });
        }
        map.value.push(row);
      }
      for (let bomb = 0; bomb < bombsize.value; bomb++) {
        const index = Math.floor(Math.random() * noBombMap.length);
        const cell = noBombMap[index];
        map.value[cell.y][cell.x].hasBomb = true;
        noBombMap.splice(index, 1);
      }
    };

    const init = () => {
      timer.value.stop();
      timer.value.reset();
      map.value = [];
      openedCount = 0;
      flagCount.value = 0;
      isCompleted = false;
      isStarted.value = false;
      isLost.value = false;

      createMap();
    };

    const toggleShownConfig = () => {
      shownConfig.value = !shownConfig.value;
    };

    const selectLevel = (result: string) => {
      level.value = result;

      switch (level.value) {
        case LEVEL_LIST[0]:
          xsize = 10;
          ysize = 10;
          bombsize.value = 10;
          break;
        case LEVEL_LIST[1]:
          xsize = 20;
          ysize = 20;
          bombsize.value = 80;
          break;
        case LEVEL_LIST[2]:
          xsize = 30;
          ysize = 30;
          bombsize.value = 225;
          break;
        default:
      }

      toggleShownConfig();
      init();
    };

    const getNeighbours = (cell: Cell) => {
      const list = [];
      const minX = Math.max(0, cell.x - 1);
      const maxX = Math.min(xsize - 1, cell.x + 1);
      const minY = Math.max(0, cell.y - 1);
      const maxY = Math.min(ysize - 1, cell.y + 1);
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
      if (
        cell.isOpened ||
        cell.contents == FLAG ||
        isLost.value ||
        isCompleted
      ) {
        return;
      }

      if (cell.hasBomb) {
        if (!isStarted.value) {
          init();
          open(map.value[cell.y][cell.x]);

          return;
        }

        isLost.value = true;
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
        if (!isStarted.value) {
          if (neighboursBombsize != 0) {
            init();
            open(map.value[cell.y][cell.x]);

            return;
          } else {
            timer.value.start();
            isStarted.value = true;
          }
        }

        cell.isOpened = true;

        if (neighboursBombsize == 0) {
          neighbours.forEach((neighbour: Cell) => {
            if (!neighbour.isOpened && neighbour.contents != FLAG) {
              open(neighbour);
            }
          });
        } else {
          cell.contents = neighboursBombsize.toString();
        }

        openedCount++;

        if (xsize * ysize == openedCount + bombsize.value) {
          timer.value.stop();
          isCompleted = true;
          alert(
            "おめでとう\r\nクリアタイム：" +
              Math.floor(timer.value.timer / 60) +
              "分" +
              (timer.value.timer % 60) +
              "秒"
          );
        }
      }
    };

    const check = (cell: Cell) => {
      if (cell.isOpened || isLost.value) {
        return;
      }

      if (cell.contents == FLAG) {
        cell.contents = "";
        flagCount.value--;
      } else {
        cell.contents = FLAG;
        flagCount.value++;
      }
    };

    onMounted(() => {
      init();
    });

    return {
      LEVEL_LIST,
      BOMB,
      shownConfig,
      level,
      isStarted,
      isLost,
      bombsize,
      map,
      flagCount,
      timer,
      toggleShownConfig,
      selectLevel,
      init,
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
  border: solid;
  text-align: center;
}
.minesweeper .header {
  font-size: 3vmin;
  height: 5vmin;
}
.minesweeper .header .left {
  position: absolute;
  left: 10px;
}
.minesweeper .config {
  position: absolute;
  right: 0;
  top: 0;
}
.minesweeper .config img {
  height: 3vmin;
  vertical-align: top;
  margin: 1vmin;
}
.minesweeper .selectlevel button {
  width: 30%;
  margin: 1%;
  font-size: 1em;
}
.minesweeper .row:before {
  content: "";
}
.minesweeper .row {
  display: flex;
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
.easy .mcell {
  width: 10%;
  font-size: 4vmin;
}
.normal .mcell {
  width: 5%;
  font-size: 2vmin;
}
.hard .mcell {
  width: 3.333%;
  font-size: 1.5vmin;
}
</style>
