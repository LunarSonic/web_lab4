const HtmlWebpackPlugin = require("html-webpack-plugin");
const ModuleFederationPlugin = require("webpack/lib/container/ModuleFederationPlugin");
const path = require("path");

module.exports = {
    mode: "production",
    entry: "./src/index.js",
    output: {
        filename: "[name].bundle.js",
        path: path.resolve(__dirname, "dist"),
        publicPath: "http://localhost:9000/",
        clean: true
    },
    module: {
        rules: [
            {
                test: /\.(js|jsx)$/,
                exclude: /node_modules/,
                use: {
                    loader: "babel-loader",
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react"]
                    }
                }
            },
            {
                test: /\.css$/,
                use: ["style-loader", "css-loader"]
            },
        ]
    },
    resolve: {
        extensions: [".js", ".jsx"]
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: "./public/index.html",
            title: "Host App"
        }),
        new ModuleFederationPlugin({
            name: "host",
            remotes: {
                shared: "shared@http://localhost:9001/remoteEntry.js",
                inputapp: "inputapp@http://localhost:9002/remoteEntry.js",
                resultapp: "resultapp@http://localhost:9003/remoteEntry.js"
            },
            shared: {
                react: {
                    singleton: true,
                    requiredVersion: "18.2.0"
                },
                "react-dom": {
                    singleton: true,
                    requiredVersion: "18.2.0"
                },
                "react-router-dom": {
                    singleton: true,
                    requiredVersion: "6.20.0"
                },
                "react-redux": {
                    singleton: true,
                    requiredVersion: "8.1.3"
                },
                "@reduxjs/toolkit": {
                    singleton: true,
                    requiredVersion: "1.9.7"
                }
            }
        })
    ]
};
