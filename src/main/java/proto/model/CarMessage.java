// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: model.proto

package proto.model;

/**
 * Protobuf type {@code CarMessage}
 */
public  final class CarMessage extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:CarMessage)
    CarMessageOrBuilder {
private static final long serialVersionUID = 0L;
  // Use CarMessage.newBuilder() to construct.
  private CarMessage(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private CarMessage() {
    carId_ = "";
    laneId_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new CarMessage();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private CarMessage(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            carId_ = s;
            break;
          }
          case 17: {

            length_ = input.readDouble();
            break;
          }
          case 25: {

            acceleration_ = input.readDouble();
            break;
          }
          case 33: {

            speed_ = input.readDouble();
            break;
          }
          case 41: {

            maxSpeed_ = input.readDouble();
            break;
          }
          case 50: {
            java.lang.String s = input.readStringRequireUtf8();

            laneId_ = s;
            break;
          }
          case 57: {

            positionOnLane_ = input.readDouble();
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return proto.model.Model.internal_static_CarMessage_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return proto.model.Model.internal_static_CarMessage_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            proto.model.CarMessage.class, proto.model.CarMessage.Builder.class);
  }

  public static final int CARID_FIELD_NUMBER = 1;
  private volatile java.lang.Object carId_;
  /**
   * <code>string carId = 1;</code>
   * @return The carId.
   */
  public java.lang.String getCarId() {
    java.lang.Object ref = carId_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      carId_ = s;
      return s;
    }
  }
  /**
   * <code>string carId = 1;</code>
   * @return The bytes for carId.
   */
  public com.google.protobuf.ByteString
      getCarIdBytes() {
    java.lang.Object ref = carId_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      carId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int LENGTH_FIELD_NUMBER = 2;
  private double length_;
  /**
   * <code>double length = 2;</code>
   * @return The length.
   */
  public double getLength() {
    return length_;
  }

  public static final int ACCELERATION_FIELD_NUMBER = 3;
  private double acceleration_;
  /**
   * <code>double acceleration = 3;</code>
   * @return The acceleration.
   */
  public double getAcceleration() {
    return acceleration_;
  }

  public static final int SPEED_FIELD_NUMBER = 4;
  private double speed_;
  /**
   * <code>double speed = 4;</code>
   * @return The speed.
   */
  public double getSpeed() {
    return speed_;
  }

  public static final int MAXSPEED_FIELD_NUMBER = 5;
  private double maxSpeed_;
  /**
   * <code>double maxSpeed = 5;</code>
   * @return The maxSpeed.
   */
  public double getMaxSpeed() {
    return maxSpeed_;
  }

  public static final int LANEID_FIELD_NUMBER = 6;
  private volatile java.lang.Object laneId_;
  /**
   * <code>string laneId = 6;</code>
   * @return The laneId.
   */
  public java.lang.String getLaneId() {
    java.lang.Object ref = laneId_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      laneId_ = s;
      return s;
    }
  }
  /**
   * <code>string laneId = 6;</code>
   * @return The bytes for laneId.
   */
  public com.google.protobuf.ByteString
      getLaneIdBytes() {
    java.lang.Object ref = laneId_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      laneId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int POSITIONONLANE_FIELD_NUMBER = 7;
  private double positionOnLane_;
  /**
   * <code>double positionOnLane = 7;</code>
   * @return The positionOnLane.
   */
  public double getPositionOnLane() {
    return positionOnLane_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getCarIdBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, carId_);
    }
    if (length_ != 0D) {
      output.writeDouble(2, length_);
    }
    if (acceleration_ != 0D) {
      output.writeDouble(3, acceleration_);
    }
    if (speed_ != 0D) {
      output.writeDouble(4, speed_);
    }
    if (maxSpeed_ != 0D) {
      output.writeDouble(5, maxSpeed_);
    }
    if (!getLaneIdBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 6, laneId_);
    }
    if (positionOnLane_ != 0D) {
      output.writeDouble(7, positionOnLane_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getCarIdBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, carId_);
    }
    if (length_ != 0D) {
      size += com.google.protobuf.CodedOutputStream
        .computeDoubleSize(2, length_);
    }
    if (acceleration_ != 0D) {
      size += com.google.protobuf.CodedOutputStream
        .computeDoubleSize(3, acceleration_);
    }
    if (speed_ != 0D) {
      size += com.google.protobuf.CodedOutputStream
        .computeDoubleSize(4, speed_);
    }
    if (maxSpeed_ != 0D) {
      size += com.google.protobuf.CodedOutputStream
        .computeDoubleSize(5, maxSpeed_);
    }
    if (!getLaneIdBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, laneId_);
    }
    if (positionOnLane_ != 0D) {
      size += com.google.protobuf.CodedOutputStream
        .computeDoubleSize(7, positionOnLane_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof proto.model.CarMessage)) {
      return super.equals(obj);
    }
    proto.model.CarMessage other = (proto.model.CarMessage) obj;

    if (!getCarId()
        .equals(other.getCarId())) return false;
    if (java.lang.Double.doubleToLongBits(getLength())
        != java.lang.Double.doubleToLongBits(
            other.getLength())) return false;
    if (java.lang.Double.doubleToLongBits(getAcceleration())
        != java.lang.Double.doubleToLongBits(
            other.getAcceleration())) return false;
    if (java.lang.Double.doubleToLongBits(getSpeed())
        != java.lang.Double.doubleToLongBits(
            other.getSpeed())) return false;
    if (java.lang.Double.doubleToLongBits(getMaxSpeed())
        != java.lang.Double.doubleToLongBits(
            other.getMaxSpeed())) return false;
    if (!getLaneId()
        .equals(other.getLaneId())) return false;
    if (java.lang.Double.doubleToLongBits(getPositionOnLane())
        != java.lang.Double.doubleToLongBits(
            other.getPositionOnLane())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + CARID_FIELD_NUMBER;
    hash = (53 * hash) + getCarId().hashCode();
    hash = (37 * hash) + LENGTH_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        java.lang.Double.doubleToLongBits(getLength()));
    hash = (37 * hash) + ACCELERATION_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        java.lang.Double.doubleToLongBits(getAcceleration()));
    hash = (37 * hash) + SPEED_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        java.lang.Double.doubleToLongBits(getSpeed()));
    hash = (37 * hash) + MAXSPEED_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        java.lang.Double.doubleToLongBits(getMaxSpeed()));
    hash = (37 * hash) + LANEID_FIELD_NUMBER;
    hash = (53 * hash) + getLaneId().hashCode();
    hash = (37 * hash) + POSITIONONLANE_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        java.lang.Double.doubleToLongBits(getPositionOnLane()));
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static proto.model.CarMessage parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static proto.model.CarMessage parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static proto.model.CarMessage parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static proto.model.CarMessage parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static proto.model.CarMessage parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static proto.model.CarMessage parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static proto.model.CarMessage parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static proto.model.CarMessage parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static proto.model.CarMessage parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static proto.model.CarMessage parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static proto.model.CarMessage parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static proto.model.CarMessage parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(proto.model.CarMessage prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code CarMessage}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:CarMessage)
      proto.model.CarMessageOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return proto.model.Model.internal_static_CarMessage_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return proto.model.Model.internal_static_CarMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              proto.model.CarMessage.class, proto.model.CarMessage.Builder.class);
    }

    // Construct using proto.model.CarMessage.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      carId_ = "";

      length_ = 0D;

      acceleration_ = 0D;

      speed_ = 0D;

      maxSpeed_ = 0D;

      laneId_ = "";

      positionOnLane_ = 0D;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return proto.model.Model.internal_static_CarMessage_descriptor;
    }

    @java.lang.Override
    public proto.model.CarMessage getDefaultInstanceForType() {
      return proto.model.CarMessage.getDefaultInstance();
    }

    @java.lang.Override
    public proto.model.CarMessage build() {
      proto.model.CarMessage result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public proto.model.CarMessage buildPartial() {
      proto.model.CarMessage result = new proto.model.CarMessage(this);
      result.carId_ = carId_;
      result.length_ = length_;
      result.acceleration_ = acceleration_;
      result.speed_ = speed_;
      result.maxSpeed_ = maxSpeed_;
      result.laneId_ = laneId_;
      result.positionOnLane_ = positionOnLane_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof proto.model.CarMessage) {
        return mergeFrom((proto.model.CarMessage)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(proto.model.CarMessage other) {
      if (other == proto.model.CarMessage.getDefaultInstance()) return this;
      if (!other.getCarId().isEmpty()) {
        carId_ = other.carId_;
        onChanged();
      }
      if (other.getLength() != 0D) {
        setLength(other.getLength());
      }
      if (other.getAcceleration() != 0D) {
        setAcceleration(other.getAcceleration());
      }
      if (other.getSpeed() != 0D) {
        setSpeed(other.getSpeed());
      }
      if (other.getMaxSpeed() != 0D) {
        setMaxSpeed(other.getMaxSpeed());
      }
      if (!other.getLaneId().isEmpty()) {
        laneId_ = other.laneId_;
        onChanged();
      }
      if (other.getPositionOnLane() != 0D) {
        setPositionOnLane(other.getPositionOnLane());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      proto.model.CarMessage parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (proto.model.CarMessage) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object carId_ = "";
    /**
     * <code>string carId = 1;</code>
     * @return The carId.
     */
    public java.lang.String getCarId() {
      java.lang.Object ref = carId_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        carId_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string carId = 1;</code>
     * @return The bytes for carId.
     */
    public com.google.protobuf.ByteString
        getCarIdBytes() {
      java.lang.Object ref = carId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        carId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string carId = 1;</code>
     * @param value The carId to set.
     * @return This builder for chaining.
     */
    public Builder setCarId(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      carId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string carId = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearCarId() {
      
      carId_ = getDefaultInstance().getCarId();
      onChanged();
      return this;
    }
    /**
     * <code>string carId = 1;</code>
     * @param value The bytes for carId to set.
     * @return This builder for chaining.
     */
    public Builder setCarIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      carId_ = value;
      onChanged();
      return this;
    }

    private double length_ ;
    /**
     * <code>double length = 2;</code>
     * @return The length.
     */
    public double getLength() {
      return length_;
    }
    /**
     * <code>double length = 2;</code>
     * @param value The length to set.
     * @return This builder for chaining.
     */
    public Builder setLength(double value) {
      
      length_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>double length = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearLength() {
      
      length_ = 0D;
      onChanged();
      return this;
    }

    private double acceleration_ ;
    /**
     * <code>double acceleration = 3;</code>
     * @return The acceleration.
     */
    public double getAcceleration() {
      return acceleration_;
    }
    /**
     * <code>double acceleration = 3;</code>
     * @param value The acceleration to set.
     * @return This builder for chaining.
     */
    public Builder setAcceleration(double value) {
      
      acceleration_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>double acceleration = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearAcceleration() {
      
      acceleration_ = 0D;
      onChanged();
      return this;
    }

    private double speed_ ;
    /**
     * <code>double speed = 4;</code>
     * @return The speed.
     */
    public double getSpeed() {
      return speed_;
    }
    /**
     * <code>double speed = 4;</code>
     * @param value The speed to set.
     * @return This builder for chaining.
     */
    public Builder setSpeed(double value) {
      
      speed_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>double speed = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearSpeed() {
      
      speed_ = 0D;
      onChanged();
      return this;
    }

    private double maxSpeed_ ;
    /**
     * <code>double maxSpeed = 5;</code>
     * @return The maxSpeed.
     */
    public double getMaxSpeed() {
      return maxSpeed_;
    }
    /**
     * <code>double maxSpeed = 5;</code>
     * @param value The maxSpeed to set.
     * @return This builder for chaining.
     */
    public Builder setMaxSpeed(double value) {
      
      maxSpeed_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>double maxSpeed = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearMaxSpeed() {
      
      maxSpeed_ = 0D;
      onChanged();
      return this;
    }

    private java.lang.Object laneId_ = "";
    /**
     * <code>string laneId = 6;</code>
     * @return The laneId.
     */
    public java.lang.String getLaneId() {
      java.lang.Object ref = laneId_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        laneId_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string laneId = 6;</code>
     * @return The bytes for laneId.
     */
    public com.google.protobuf.ByteString
        getLaneIdBytes() {
      java.lang.Object ref = laneId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        laneId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string laneId = 6;</code>
     * @param value The laneId to set.
     * @return This builder for chaining.
     */
    public Builder setLaneId(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      laneId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string laneId = 6;</code>
     * @return This builder for chaining.
     */
    public Builder clearLaneId() {
      
      laneId_ = getDefaultInstance().getLaneId();
      onChanged();
      return this;
    }
    /**
     * <code>string laneId = 6;</code>
     * @param value The bytes for laneId to set.
     * @return This builder for chaining.
     */
    public Builder setLaneIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      laneId_ = value;
      onChanged();
      return this;
    }

    private double positionOnLane_ ;
    /**
     * <code>double positionOnLane = 7;</code>
     * @return The positionOnLane.
     */
    public double getPositionOnLane() {
      return positionOnLane_;
    }
    /**
     * <code>double positionOnLane = 7;</code>
     * @param value The positionOnLane to set.
     * @return This builder for chaining.
     */
    public Builder setPositionOnLane(double value) {
      
      positionOnLane_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>double positionOnLane = 7;</code>
     * @return This builder for chaining.
     */
    public Builder clearPositionOnLane() {
      
      positionOnLane_ = 0D;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:CarMessage)
  }

  // @@protoc_insertion_point(class_scope:CarMessage)
  private static final proto.model.CarMessage DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new proto.model.CarMessage();
  }

  public static proto.model.CarMessage getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<CarMessage>
      PARSER = new com.google.protobuf.AbstractParser<CarMessage>() {
    @java.lang.Override
    public CarMessage parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new CarMessage(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<CarMessage> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<CarMessage> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public proto.model.CarMessage getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

