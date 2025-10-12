import java.util.ArrayList;
import java.util.List;

class LocTuple<V, S> {
    private V first;
    private S second;

    public LocTuple(V first, S second) {
        this.first = first;
        this.second = second;
    }

    public V getFirst() {return first;}
    public S getSecond() {return second;}

    public void setFirst(V f) {this.first = f;}
    public void setSecond(S s) {this.second = s;}
}

abstract class Message {
    public abstract void addInput(int input);
    public abstract boolean isMessageEmpty();
    public abstract boolean isMessageComplete();
    public abstract void printState();
}

class WheelVelocityMessage extends Message {
    private int leftSpeed;
    private int rightSpeed;
    
    public WheelVelocityMessage() {
        this.leftSpeed = -1;
        this.rightSpeed = -1;
    }
    
    public void setLeftSpeed(int speed) {this.leftSpeed = speed;}
    public void setRightSpeed(int speed) {this.rightSpeed = speed;}

    public int getLeftSpeed() {return leftSpeed;}
    public int getRightSpeed() {return rightSpeed;}

    @Override
    public void addInput(int input) {
        if (this.leftSpeed == -1) {
            setLeftSpeed(input);
        } else {
            setRightSpeed(input);
        }
    }

    @Override
    public boolean isMessageEmpty() {
        return (this.leftSpeed == -1 && this.rightSpeed == -1);
    }

    @Override
    public boolean isMessageComplete() {
        return (this.leftSpeed != -1 && this.rightSpeed != -1);
    }

    @Override
    public void printState() {
        System.out.printf("Left speed: %d, right speed: %d \n", getLeftSpeed(), getRightSpeed());
    }
}

class VisionTargetMessage extends Message {
    private int numTargets;
    private List<LocTuple<Integer, Integer>> targets;

    public VisionTargetMessage() {
        this.numTargets = -1;
        this.targets = new ArrayList<>();
    }

    private void setNumTargets(int num) {this.numTargets = num;}
    public int getNumTargets() {return numTargets;}
    public List<LocTuple<Integer, Integer>> getTargets() {return targets;}

    @Override
    public void addInput(int input) {
        if (numTargets == -1) {
            setNumTargets(input);
            return;
        }

        int size = targets.size();
        if (size == 0) {
            targets.add(new LocTuple<Integer, Integer>(input, null));
        } else {
            var last = targets.get(size - 1);
            if (last.getSecond() == null) {
                last.setSecond(input);
            } else {
                targets.add(new LocTuple<Integer, Integer>(input, null));
            }
        }
    }

    @Override
    public boolean isMessageEmpty() {
        return (targets.size() == 0);
    }

    @Override
    public boolean isMessageComplete() {
        return (targets.size() == numTargets && targets.get(targets.size() - 1).getSecond() != null);
    }
    
    @Override
    public void printState() {
        System.out.printf("Num targets: %d \n", numTargets);
        for (int i = 0; i < targets.size(); i++) {
            LocTuple<Integer, Integer> t = targets.get(i);
            System.out.printf("%dth target has X loc: %d, Y loc: %d \n", i+1, t.getFirst(), t.getSecond());
        }
    }
}

class EmptyMessage extends Message {

    @Override
    public void addInput(int input) {}

    @Override
    public boolean isMessageEmpty() {return true;}

    @Override
    public boolean isMessageComplete() {return false;}

    @Override
    public void printState() {
        System.out.println("EmptyMessage does not have state");
    }
}


public class Messages {
    private boolean initializedMessage;
    private Message messageBuffer;
    private List<Message> messages;

    public Messages() {
        this.initializedMessage = false;
        this.messageBuffer = new EmptyMessage();
        this.messages = new ArrayList<>();
    }

    /**
     * @word the next word provided to the input stream
     * If buffer is empty, uses current word to determine message type.
     * Otherwise adds word to message buffer then checks if message buffer is full. 
     * If buffer becomes full, adds to message to message list and clears buffer.
     */
    public void processNextWord(int word) {
        if (messageBuffer.isMessageEmpty() && !initializedMessage) {
            if (word == 1) {
                this.messageBuffer = new WheelVelocityMessage();
            // use else since assuming valid message input sequence
            } else {
                this.messageBuffer = new VisionTargetMessage();
            }
            this.initializedMessage = true;
        } else if (!messageBuffer.isMessageComplete()) {
            messageBuffer.addInput(word);
        }

        if (messageBuffer.isMessageComplete()) {
            messages.add(messageBuffer);
            this.messageBuffer = new EmptyMessage();
            this.initializedMessage = false;
        }
    }

    public List<Message> getMessages() {
        return messages;
    }

    public static void main(String[] args) {
        Messages m = new Messages();
        int[] words = new int[]{1, 5, 8, 2, 3, 2, 2, 1, 5, 7, 1, 2, 1, 5, 6, 1, 2, 3};
        for (int i = 0; i < words.length; i++) {
            m.processNextWord(words[i]);
        }

        // debug
        var messages = m.getMessages();
        messages.stream().forEach(message -> message.printState());
     }

}
