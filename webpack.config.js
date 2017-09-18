const webpack = require("webpack");
const path = require("path");

module.exports = function(env) {

  function getMode(env) {
    if (env && typeof env.mode !== 'undefined') return env.mode;
    return 'dev';
  }

  const MODE = getMode(env);

  const config = {
    entry: path.resolve(__dirname, "build/classes/kotlin/main/nirvana-player-react_main.js"),
    output: {
      path: path.resolve(__dirname, "build"),
      filename: "bundle.js"
    },
    module: {
      loaders: [
        {
          test: /\.styl$/,
          loader: 'style-loader!css-loader!stylus-loader'
        }
      ]
    },
    resolve: {
      alias: {
        Style: path.resolve(__dirname, 'src/main/stylus')
      }
    },
    plugins: []
  };

  if (MODE === 'prod') {
    config.entry = path.resolve(__dirname, "build/classes/kotlin/main/min/nirvana-player-react_main.js");

    config.resolve.alias.kotlin = path.resolve(__dirname, "build/classes/kotlin/main/min/kotlin.js");
    config.resolve.alias['kotlinx-html-js'] = path.resolve(__dirname, "build/classes/kotlin/main/min/kotlinx-html-js.js");
    config.plugins.push(new webpack.optimize.UglifyJsPlugin());
  }

  return config;
};
