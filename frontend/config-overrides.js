const { injectBabelPlugin } = require("react-app-rewired");
const rewireMobX = require("react-app-rewire-mobx");
const {
    override,
    disableEsLint,
    addDecoratorsLegacy,
    fixBabelImports,
} = require("customize-cra");
module.exports = override(
    disableEsLint(),
    addDecoratorsLegacy(),
    fixBabelImports("react-app-rewire-mobx", {
    libraryDirectory: "",
    camel2DashComponentName: false
}),
);
