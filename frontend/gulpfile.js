var gulp = require('gulp');
var debug = require('gulp-debug');
var webpack = require('webpack');
var webpackStream = require('webpack-stream');

gulp.task('build', function() {
    return gulp.src('./src')
        .pipe(debug())
        .pipe(webpackStream(require('./webpack.config.js'), webpack)
            .on('error', function (err) {
                console.log(err);
            })
        )
        .pipe(gulp.dest('./dist'))
});