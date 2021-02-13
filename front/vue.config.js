module.exports = {
  outputDir: "../src/main/resources/static",
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
      filename: "index.html"
    },
    game: {
      entry: "src/game/main.ts",
      filename: "game.html"
    }
  }
};
