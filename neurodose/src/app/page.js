// src/app/page.js
'use client';

import dynamic from 'next/dynamic'

// Use dynamic import with ssr disabled for components that need client-side only rendering
const NeuroDose = dynamic(() => import('@/components/NeuroDose'), {
  ssr: false
})

export default function Page() {
  return (
    <main>
      <NeuroDose />
    </main>
  );
}