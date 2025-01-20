import React, { useState, useEffect } from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { Card, CardHeader, CardTitle, CardContent } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Alert, AlertDescription } from '@/components/ui/alert';

// Compound properties with half-lives and recommended dosing intervals
const compounds = {
  caffeine: {
    name: 'Caffeine',
    halfLife: 5, // hours
    color: '#ff7f0e',
    maxDailyDose: 400, // mg
    interactionNotes: {
      'l-theanine': 'Synergistic - May reduce jitters',
      ginseng: 'Use caution - May increase stimulant effects'
    }
  },
  'l-theanine': {
    name: 'L-Theanine',
    halfLife: 3, // hours
    color: '#2ca02c',
    maxDailyDose: 400, // mg
    interactionNotes: {
      caffeine: 'Synergistic - May reduce caffeine jitters',
      ginseng: 'Generally safe combination'
    }
  },
  ginseng: {
    name: 'Ginseng',
    halfLife: 24, // hours
    color: '#d62728',
    maxDailyDose: 400, // mg
    interactionNotes: {
      caffeine: 'Use caution - May increase stimulant effects',
      'l-theanine': 'Generally safe combination'
    }
  }
};

const SupplementTracker = () => {
  const [doses, setDoses] = useState([]);
  const [newDose, setNewDose] = useState({
    compound: 'caffeine',
    amount: 100,
    timestamp: new Date().toISOString().slice(0, 16)
  });
  
  const calculateConcentration = (dose, currentTime) => {
    const compound = compounds[dose.compound];
    const hoursDiff = (currentTime - new Date(dose.timestamp)) / (1000 * 60 * 60);
    const halfLives = hoursDiff / compound.halfLife;
    return dose.amount * Math.pow(0.5, halfLives);
  };

  const generateTimePoints = () => {
    if (doses.length === 0) return [];
    
    const now = new Date();
    const earliest = Math.min(...doses.map(d => new Date(d.timestamp)));
    const latest = new Date(Math.max(...doses.map(d => new Date(d.timestamp))));
    latest.setHours(latest.getHours() + 24); // Show 24 hours after last dose
    
    const timePoints = [];
    let current = new Date(earliest);
    
    while (current <= latest) {
      const point = {
        time: new Date(current),
        caffeine: 0,
        'l-theanine': 0,
        ginseng: 0
      };
      
      // Calculate concentration for each dose
      doses.forEach(dose => {
        point[dose.compound] += calculateConcentration(dose, current);
      });
      
      timePoints.push(point);
      current.setHours(current.getHours() + 1);
    }
    
    return timePoints;
  };

  const addDose = () => {
    const compound = compounds[newDose.compound];
    const existingAmount = doses
      .filter(d => d.compound === newDose.compound)
      .reduce((sum, d) => sum + calculateConcentration(d, new Date(newDose.timestamp)), 0);
      
    if (existingAmount + Number(newDose.amount) > compound.maxDailyDose) {
      alert(`Warning: This dose would exceed the recommended daily maximum for ${compound.name}`);
      return;
    }
    
    setDoses([...doses, { ...newDose, amount: Number(newDose.amount) }]);
  };

  const checkInteractions = () => {
    const currentLevels = {};
    Object.keys(compounds).forEach(compound => {
      currentLevels[compound] = doses
        .filter(d => d.compound === compound)
        .reduce((sum, d) => sum + calculateConcentration(d, new Date()), 0);
    });
    
    const warnings = [];
    Object.entries(currentLevels).forEach(([compound, level]) => {
      if (level > 0) {
        Object.entries(currentLevels).forEach(([otherCompound, otherLevel]) => {
          if (otherLevel > 0 && compound !== otherCompound) {
            const interaction = compounds[compound].interactionNotes[otherCompound];
            if (interaction && interaction.includes('caution')) {
              warnings.push(`${compounds[compound].name} + ${compounds[otherCompound].name}: ${interaction}`);
            }
          }
        });
      }
    });
    
    return warnings;
  };

  return (
    <div className="p-4 max-w-4xl mx-auto">
      <Card>
        <CardHeader>
          <CardTitle>Supplement Half-life Tracker</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="space-y-4">
            <div className="flex gap-4">
              <select 
                className="p-2 border rounded"
                value={newDose.compound}
                onChange={e => setNewDose({...newDose, compound: e.target.value})}
              >
                {Object.keys(compounds).map(c => (
                  <option key={c} value={c}>{compounds[c].name}</option>
                ))}
              </select>
              
              <Input
                type="number"
                placeholder="Amount (mg)"
                value={newDose.amount}
                onChange={e => setNewDose({...newDose, amount: e.target.value})}
                className="w-32"
              />
              
              <Input
                type="datetime-local"
                value={newDose.timestamp}
                onChange={e => setNewDose({...newDose, timestamp: e.target.value})}
              />
              
              <Button onClick={addDose}>Add Dose</Button>
            </div>

            {checkInteractions().map((warning, i) => (
              <Alert key={i} variant="destructive">
                <AlertDescription>{warning}</AlertDescription>
              </Alert>
            ))}

            <div className="h-96">
              <ResponsiveContainer width="100%" height="100%">
                <LineChart data={generateTimePoints()} margin={{ top: 5, right: 30, left: 20, bottom: 5 }}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis 
                    dataKey="time" 
                    tickFormatter={time => time.toLocaleTimeString()}
                  />
                  <YAxis label={{ value: 'Concentration (mg)', angle: -90, position: 'insideLeft' }} />
                  <Tooltip
                    labelFormatter={time => new Date(time).toLocaleString()}
                    formatter={(value, name) => [
                      `${Math.round(value)} mg`,
                      compounds[name].name
                    ]}
                  />
                  <Legend />
                  {Object.keys(compounds).map(compound => (
                    <Line
                      key={compound}
                      type="monotone"
                      dataKey={compound}
                      stroke={compounds[compound].color}
                      dot={false}
                    />
                  ))}
                </LineChart>
              </ResponsiveContainer>
            </div>

            <div className="mt-4">
              <h3 className="font-bold">Current Doses:</h3>
              <ul className="list-disc pl-6">
                {doses.map((dose, i) => (
                  <li key={i}>
                    {compounds[dose.compound].name}: {dose.amount}mg at{' '}
                    {new Date(dose.timestamp).toLocaleString()}
                  </li>
                ))}
              </ul>
            </div>
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default SupplementTracker;