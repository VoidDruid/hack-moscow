const proxy = require('http-proxy-middleware');
console.log('proxy is imported')
module.exports = function(app) {
    app.use(proxy('*/api',{
            target: 'http://spacehub.su/api',
            changeOrigin: true,
        })
    );
};
