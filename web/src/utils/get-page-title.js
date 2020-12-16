import defaultSettings from '@/settings'

const title = defaultSettings.title || 'HServer System'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
