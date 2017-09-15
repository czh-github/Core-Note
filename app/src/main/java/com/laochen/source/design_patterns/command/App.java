/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.laochen.source.design_patterns.command;

/**
 * 命令模式：是一种行为模式，把要执行的一个动作或未来会触发的一个事件的所有信息封装到一个对象里面。
 * 信息包括方法名，对象，方法参数值。
 * 四个术语：
 * 命令（知道接收者，调用接收者的方法）
 * 接收者（被命令调用其方法），
 * 调用者（知道怎样执行命令，不需知道具体命令，只需知道命令接口）
 * 客户端(决定什么时候执行哪个命令)
 *
 * The Command pattern is a behavioral design pattern in which an object is used to encapsulate all
 * information needed to perform an action or trigger an event at a later time. This information
 * includes the method name, the object that owns the method and values for the method parameters.
 * <p>
 * Four terms always associated with the command pattern are command, receiver, invoker and client.
 * A command object (spell) knows about the receiver (target) and invokes a method of the receiver.
 * Values for parameters of the receiver method are stored in the command. The receiver then does
 * the work. An invoker object (wizard) knows how to execute a command, and optionally does
 * bookkeeping about the command execution. The invoker does not know anything about a concrete
 * command, it knows only about command interface. Both an invoker object and several command
 * objects are held by a client object (app). The client decides which commands to execute at which
 * points. To execute a command, it passes the command object to the invoker object.
 * <p>
 * In other words, in this example the wizard casts spells on the goblin. The wizard keeps track of
 * the previous spells cast, so it is easy to undo them. In addition, the wizard keeps track of the
 * spells undone, so they can be redone.
 * 
 * 
 */
public class App {

  /**
   * Program entry point
   * 
   * @param args command line args
   */
  public static void main(String[] args) {
    Wizard wizard = new Wizard(); // 命令的执行者
    Goblin goblin = new Goblin(); // 命令的接收者

    goblin.printStatus();

    wizard.castSpell(new ShrinkSpell(), goblin);
    goblin.printStatus();

    wizard.castSpell(new InvisibilitySpell(), goblin);
    goblin.printStatus();

    wizard.undoLastSpell();
    goblin.printStatus();

    wizard.undoLastSpell();
    goblin.printStatus();

    wizard.redoLastSpell();
    goblin.printStatus();

    wizard.redoLastSpell();
    goblin.printStatus();
  }
}
