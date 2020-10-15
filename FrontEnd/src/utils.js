/**
 * @param {Date} date object to format
 * @return {string} with format like this '2020-09-07T17:00+11:00'
 */

const formatPad = (num) => String(num).padStart("2", "0");

// month and day need to be converted from 0 indexed to 1 indexed
export const formatDate = (date) => {
  const year = date.getUTCFullYear();
  const month = formatPad(date.getUTCMonth() + 1);
  const day = formatPad(date.getUTCDate() + 1);
  const hour = formatPad(date.getUTCHours());
  const minute = formatPad(date.getUTCMinutes());
  return `${year}-${month}-${day}T${hour}:${minute}+00:00`;
};
