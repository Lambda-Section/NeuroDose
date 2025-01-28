// src/app/layout.js
import '@/app/globals.css'

export const metadata = {
    title: 'NeuroDose',
    description: 'Advanced Supplement Tracker',
};
  
  export default function RootLayout({ children }) {
    return (
      <html lang="en">
        <body suppressHydrationWarning={true}>{children}</body>
      </html>
    );
  }
