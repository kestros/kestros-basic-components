/*
 *      Copyright (C) 2020  Kestros, Inc.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

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