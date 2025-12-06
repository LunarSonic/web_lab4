const HtmlWebpackPlugin = require("html-webpack-plugin");
const ModuleFederationPlugin = require("webpack/lib/container/ModuleFederationPlugin");
const path = require("path");

module.exports = {
    mode: "production",
    entry: "./src/index.js",
    output: {
        filename: "[name].bundle.js",
        path: path.resolve(__dirname, 'dist'),
        publicPath: "http://localhost:9001/",
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
                use: ['style-loader', 'css-loader']
            }
        ]
    },
    resolve: {
        extensions: ['.js', '.jsx']
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: "./public/index.html",
            title: "Shared App"
        }),
        new ModuleFederationPlugin({
            name: "shared",
            filename: "remoteEntry.js",
            exposes: {
                "./store": "./src/store/store",
                "./authApi": "./src/store/api/authApi",
                "./geometryApi": "./src/store/api/geometryApi",
                "./historyApi": "./src/store/api/historyApi",
                "./authSlice": "./src/store/slice/authSlice",
                "./rSlice": "./src/store/slice/rSlice",
                "./useErrorVisibility": "./src/hooks/useErrorVisibility"
            },
            shared: {
                react: {
                    singleton: true,
                    requiredVersion: "18.2.0",
                    eager: true,
                },
                "react-dom": {
                    singleton: true,
                    requiredVersion: "18.2.0",
                    eager: true,
                },
                "react-router-dom": {
                    singleton: true,
                    requiredVersion: "6.20.0",
                    eager: true,
                },
                "react-redux": {
                    singleton: true,
                    requiredVersion: "8.1.3",
                    eager: true
                },
                "@reduxjs/toolkit": {
                    singleton: true,
                    requiredVersion: "1.9.7",
                    eager: true,
                }
            }
        })
    ]
};
