var root = '',
    components = root + 'apps/',
    libs = root + '',
    designs = root + 'etc/';

var gulp = require('gulp'),
    gutil = require('gulp-util'),
    slang = require('gulp-slang');

function changeNotification(fType, eType, msg) {
  gutil.log(gutil.colors.cyan.bold(fType), 'was',
      gutil.colors.yellow.bold(eType) + '.', msg);
}

gulp.task('watch', function () {
  gutil.log('Waiting for changes.');

  // Set up our streams
  var jsWatch = gulp.watch([libs + '**/*.js']),
      lessWatch = gulp.watch([libs + '**/*.less']),
      markupWatch = gulp.watch([libs + '**/**/*.html', libs + '**/**/*.jsp']);

  jsWatch.on('change', function (ev) {
    changeNotification('Sightly file', ev, 'Slinging file to Kestros.');

    return gulp.src(ev)
    .pipe(slang({
      port: 8080
    }));
  });

  lessWatch.on('change', function (ev) {
    changeNotification('Sightly file', ev, 'Slinging file to Kestros.');

    return gulp.src(ev)
    .pipe(slang({
      port: 8080
    }));
  });

  markupWatch.on('change', function (ev) {
    changeNotification('Sightly file', ev, 'Slinging file to Kestros.');

    return gulp.src(ev)
    .pipe(slang({
      port: 8080
    }));
  });
});