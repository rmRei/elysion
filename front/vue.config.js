module.exports = {
  outputDir: "../src/main/resources/static/generated",
  filenameHashing: false,
  configureWebpack: {
    devServer: {
      watchOptions: {
        poll: true
      }
    }
  },
  pages: {
    top: {
      entry: "src/top/main.ts",
      template: "../src/main/resources/templates/top.html",
      filename: "top.html"
    },
    game: {
      entry: "src/game/main.ts",
      filename: "game.html"
    }
  }
};
