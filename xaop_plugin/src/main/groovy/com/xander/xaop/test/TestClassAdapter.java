package com.xander.xaop.test;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.util.Arrays;

public class TestClassAdapter extends ClassVisitor {

  private String className;

  private boolean isHeritedFromBlockHandler = false;

  TestClassAdapter(final ClassVisitor cv) {
    super(Opcodes.ASM5, cv);
  }

  @Override
  public void visit(int version, int access, String name, String signature, String superName,
      String[] interfaces) {
    super.visit(version, access, name, signature, superName, interfaces);
    this.isHeritedFromBlockHandler =
        Arrays.toString(interfaces).contains("com/hunter/library/timing/IBlockHandler");
    this.className = name;
  }

  @Override public MethodVisitor visitMethod(final int access, final String name, final String desc,
      final String signature, final String[] exceptions) {
    MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
    if (isHeritedFromBlockHandler) {
      return mv;
    } else {
      return mv == null ? null : new TestMethodAdapter(
          className + File.separator + name, access, desc, mv);
    }
  }
}