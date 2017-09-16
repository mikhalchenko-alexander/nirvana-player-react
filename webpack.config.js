const webpack = require("webpack");
const path = require("path");

module.exports = {
  entry: path.resolve(__dirname, "build/classes/kotlin/main/nirvana-player-react_main.js"),
  output: {
    path: path.resolve(__dirname, "build"),
    filename: "bundle.js"
  },
  plugins: [
    new webpack.optimize.UglifyJsPlugin()
  ]
};
