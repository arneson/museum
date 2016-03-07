var gulp = require('gulp');
var sass = require('gulp-sass');
var run = require('gulp-run');
var ip = require("ip");
var bower = require('gulp-bower');
var gulpFilter = require('gulp-filter');

var paths = {
  scripts: ['www/js/*.js'],
  images: 'www/img/**/*',
  scss: 'scss/',
  root: './',
  bowerDir: './bower_components'
};

gulp.task('scss', function() {
  return gulp.src('./scss/*.scss')
    .pipe(sass().on('error', sass.logError))
    .pipe(gulp.dest('./www/css'));
});
gulp.task('watch', function() {
  gulp.watch(paths.scss, ['scss','build']);
  gulp.watch(paths.scripts, ['scss','build']);
});
gulp.task('bower', function() {
    var filter = gulpFilter(['*.js', '**/*.js'], {restore: true});
    return bower()
        .pipe(filter)
        .pipe(gulp.dest('./www/js/'));
});
gulp.task('icons', function() {
    run('ionic resources').exec();
});
gulp.task('build',['bower'], function() {
    run('phonegap build').exec();
});
gulp.task('serve',['build'], function() {
    run('phonegap serve browser').exec();
    console.log('running at: ' + ip.address()+":3000");
});

gulp.task('default', ['scss','bower','build','serve','watch']);

