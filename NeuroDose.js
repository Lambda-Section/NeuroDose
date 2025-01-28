import React, { useState, useEffect, useRef } from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer, ReferenceLine } from 'recharts';
import { Card, CardHeader, CardTitle, CardContent } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Alert, AlertDescription } from '@/components/ui/alert';
import { Bell, Moon, Sun, Activity } from 'lucide-react';

// Enhanced compound database with detailed pharmacokinetics
const compounds = {
  caffeine: {
    name: 'Caffeine',
    halfLife: 5,
    color: '#ff7f0e',
    maxDailyDose: 400,
    absorptionRate: 0.5,
    bioavailability: 0.99,
    peakThreshold: 200,
    minEffectiveConc: 50,
    categories: ['stimulant', 'cognitive'],
    alternatives: ['theobromine', 'yerba-mate'],
    interactionNotes: {
      'l-theanine': 'Synergistic - May reduce jitters',
      ginseng: 'Use caution - May increase stimulant effects',
      'rhodiola': 'Synergistic for focus and energy',
      'cordyceps': 'Complementary for energy and endurance'
    }
  },
  'l-theanine': {
    name: 'L-Theanine',
    halfLife: 3,
    color: '#2ca02c',
    maxDailyDose: 400,
    absorptionRate: 0.8,
    bioavailability: 0.98,
    peakThreshold: 200,
    minEffectiveConc: 50,
    categories: ['relaxation', 'cognitive'],
    alternatives: ['glycine', 'taurine'],
    interactionNotes: {
      caffeine: 'Synergistic - May reduce caffeine jitters',
      ginseng: 'Generally safe combination',
      'ashwagandha': 'Complementary for stress reduction',
      'magnolia': 'Synergistic for relaxation'
    }
  },
  ginseng: {
    name: 'Ginseng',
    halfLife: 24,
    color: '#d62728',
    maxDailyDose: 400,
    absorptionRate: 2.0,
    bioavailability: 0.15,
    peakThreshold: 200,
    minEffectiveConc: 100,
    categories: ['adaptogen', 'energy'],
    alternatives: ['eleuthero', 'rhodiola'],
    interactionNotes: {
      caffeine: 'Use caution - May increase stimulant effects',
      'l-theanine': 'Generally safe combination',
      'rhodiola': 'Complementary adaptogenic effects',
      'cordyceps': 'Synergistic for energy'
    }
  }
};

const SupplementTracker = () => {
  const [doses, setDoses] = useState([]);
  const [notifications, setNotifications] = useState([]);
  const [sleepSchedule, setSleepSchedule] = useState({
    start: '22:00',
    end: '06:00'
  });
  const [thresholds, setThresholds] = useState(
    Object.fromEntries(
      Object.keys(compounds).map(c => [c, compounds[c].minEffectiveConc])
    )
  );
  const [newDose, setNewDose] = useState({
    compound: 'caffeine',
    amount: 100,
    timestamp: new Date().toISOString().slice(0, 16)
  });

  // Enhanced pharmacokinetic model
  const calculateConcentration = (dose, currentTime) => {
    const compound = compounds[dose.compound];
    const hoursDiff = (currentTime - new Date(dose.timestamp)) / (1000 * 60 * 60);
    
    if (hoursDiff < 0) return 0;
    
    const absorptionFactor = 1 - Math.exp(-hoursDiff / compound.absorptionRate);
    const eliminationFactor = Math.pow(0.5, hoursDiff / compound.halfLife);
    
    return dose.amount * compound.bioavailability * absorptionFactor * eliminationFactor;
  };

  // Time series data generator with peak indicators
  const generateTimePoints = () => {
    if (doses.length === 0) return [];
    
    const now = new Date();
    const earliest = Math.min(...doses.map(d => new Date(d.timestamp)));
    const latest = new Date(Math.max(...doses.map(d => new Date(d.timestamp))));
    latest.setHours(latest.getHours() + 24);
    
    const timePoints = [];
    let current = new Date(earliest);
    
    while (current <= latest) {
      const point = {
        time: current.toISOString(),
        ...Object.fromEntries(
          Object.keys(compounds).map(compound => [compound, 0])
        )
      };
      
      doses.forEach(dose => {
        point[dose.compound] += calculateConcentration(dose, current);
      });
      
      timePoints.push(point);
      current = new Date(current.getTime() + 3600000); // Add 1 hour
    }
    
    return timePoints;
  };

  // Notification system
  useEffect(() => {
    const checkNotifications = setInterval(() => {
      Object.keys(compounds).forEach(compound => {
        const currentLevel = doses
          .filter(d => d.compound === compound)
          .reduce((sum, d) => sum + calculateConcentration(d, new Date()), 0);
          
        if (currentLevel < thresholds[compound]) {
          setNotifications(prev => [
            ...prev,
            `${compounds[compound].name} level below threshold - Consider next dose`
          ]);
        }
      });
    }, 60000);
    
    return () => clearInterval(checkNotifications);
  }, [doses, thresholds]);

  return (
    <div className="p-4 max-w-6xl mx-auto">
      <Card className="bg-gray-50">
        <CardHeader>
          <CardTitle className="flex items-center gap-2">
            <Activity className="w-6 h-6" />
            Advanced Supplement Tracker
          </CardTitle>
        </CardHeader>
        <CardContent>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            {/* Input Section */}
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
                
                <Button onClick={() => setDoses([...doses, { ...newDose, amount: Number(newDose.amount) }])}>
                  Add Dose
                </Button>
              </div>

              {/* Sleep Schedule */}
              <div className="flex items-center gap-4">
                <Moon className="w-5 h-5" />
                <Input
                  type="time"
                  value={sleepSchedule.start}
                  onChange={e => setSleepSchedule({...sleepSchedule, start: e.target.value})}
                  className="w-32"
                />
                <Sun className="w-5 h-5" />
                <Input
                  type="time"
                  value={sleepSchedule.end}
                  onChange={e => setSleepSchedule({...sleepSchedule, end: e.target.value})}
                  className="w-32"
                />
              </div>

              {/* Threshold Adjustments */}
              <div className="space-y-2">
                <h3 className="font-bold">Minimum Effective Thresholds</h3>
                {Object.keys(compounds).map(compound => (
                  <div key={compound} className="flex items-center gap-2">
                    <span className="w-24">{compounds[compound].name}</span>
                    <Input
                      type="range"
                      min="0"
                      max={compounds[compound].maxDailyDose}
                      value={thresholds[compound]}
                      onChange={e => setThresholds({
                        ...thresholds,
                        [compound]: Number(e.target.value)
                      })}
                      className="w-48"
                    />
                    <span>{thresholds[compound]}mg</span>
                  </div>
                ))}
              </div>

              {/* Notifications */}
              {notifications.length > 0 && (
                <div className="space-y-2">
                  <h3 className="font-bold flex items-center gap-2">
                    <Bell className="w-5 h-5" />
                    Notifications
                  </h3>
                  {notifications.map((notification, i) => (
                    <Alert key={i}>
                      <AlertDescription>{notification}</AlertDescription>
                    </Alert>
                  ))}
                </div>
              )}
            </div>

            {/* Visualization Section */}
            <div className="space-y-4">
              <div className="h-96 bg-white p-4 rounded-lg">
                <ResponsiveContainer width="100%" height="100%">
                  <LineChart data={generateTimePoints()} margin={{ top: 5, right: 30, left: 20, bottom: 5 }}>
                    <CartesianGrid strokeDasharray="3 3" className="opacity-50" />
                    <XAxis
                      dataKey="time"
                      tickFormatter={time => new Date(time).toLocaleTimeString()}
                      stroke="#666"
                    />
                    <YAxis
                      label={{ value: 'Concentration (mg)', angle: -90, position: 'insideLeft' }}
                      stroke="#666"
                    />
                    <Tooltip
                      labelFormatter={time => new Date(time).toLocaleString()}
                      contentStyle={{ background: 'rgba(255, 255, 255, 0.9)', border: '1px solid #ccc' }}
                    />
                    <Legend />
                    {Object.keys(compounds).map(compound => (
                      <React.Fragment key={compound}>
                        <Line
                          type="monotone"
                          dataKey={compound}
                          stroke={compounds[compound].color}
                          strokeWidth={2}
                          dot={false}
                          name={compounds[compound].name}
                        />
                        <ReferenceLine
                          y={thresholds[compound]}
                          stroke={compounds[compound].color}
                          strokeDasharray="3 3"
                          label={`${compounds[compound].name} Threshold`}
                        />
                      </React.Fragment>
                    ))}
                  </LineChart>
                </ResponsiveContainer>
              </div>
            </div>
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default SupplementTracker;