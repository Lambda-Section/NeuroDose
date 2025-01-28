// BrainVisualization.jsx
import React, { useEffect, useRef } from 'react';
import * as THREE from 'three';

const BrainVisualization = ({ concentrationLevels, compounds }) => {
  const mountRef = useRef(null);

  useEffect(() => {
    if (!mountRef.current) return;

    // Scene setup
    const scene = new THREE.Scene();
    const camera = new THREE.PerspectiveCamera(75, mountRef.current.clientWidth / mountRef.current.clientHeight, 0.1, 1000);
    const renderer = new THREE.WebGLRenderer({ antialias: true, alpha: true });
    
    renderer.setSize(mountRef.current.clientWidth, mountRef.current.clientHeight);
    mountRef.current.appendChild(renderer.domElement);

    // Brain model (simplified)
    const brainGeometry = new THREE.SphereGeometry(5, 32, 32);
    const brainMaterial = new THREE.MeshPhongMaterial({
      color: 0xe0e0e0,
      transparent: true,
      opacity: 0.8,
    });
    const brain = new THREE.Mesh(brainGeometry, brainMaterial);
    scene.add(brain);

    // Compound visualization
    Object.entries(concentrationLevels).forEach(([compound, level]) => {
      if (level > 0) {
        const compoundData = compounds[compound];
        const intensity = level / compoundData.maxDailyDose;
        
        // Create particle system for compound
        const particleGeometry = new THREE.BufferGeometry();
        const particleCount = Math.floor(intensity * 1000);
        const positions = new Float32Array(particleCount * 3);
        
        for (let i = 0; i < particleCount; i++) {
          const theta = Math.random() * Math.PI * 2;
          const phi = Math.acos(2 * Math.random() - 1);
          const r = 4.5 * Math.pow(Math.random(), 1/3);
          
          positions[i * 3] = r * Math.sin(phi) * Math.cos(theta);
          positions[i * 3 + 1] = r * Math.sin(phi) * Math.sin(theta);
          positions[i * 3 + 2] = r * Math.cos(phi);
        }
        
        particleGeometry.setAttribute('position', new THREE.BufferAttribute(positions, 3));
        
        const particleMaterial = new THREE.PointsMaterial({
          color: compoundData.color,
          size: 0.1,
          transparent: true,
          opacity: 0.6 * intensity
        });
        
        const particles = new THREE.Points(particleGeometry, particleMaterial);
        scene.add(particles);
      }
    });

    // Lighting
    const ambientLight = new THREE.AmbientLight(0x404040);
    const pointLight = new THREE.PointLight(0xffffff, 1);
    pointLight.position.set(10, 10, 10);
    scene.add(ambientLight);
    scene.add(pointLight);

    camera.position.z = 15;

    // Animation
    const animate = () => {
      brain.rotation.y += 0.005;
      renderer.render(scene, camera);
      requestAnimationFrame(animate);
    };
    animate();

    // Cleanup
    return () => {
      mountRef.current?.removeChild(renderer.domElement);
      renderer.dispose();
    };
  }, [concentrationLevels, compounds]);

  return <div ref={mountRef} className="w-full h-64 rounded-lg overflow-hidden" />;
};

export default BrainVisualization;