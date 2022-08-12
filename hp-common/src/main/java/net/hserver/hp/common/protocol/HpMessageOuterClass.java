package net.hserver.hp.common.protocol;

public final class HpMessageOuterClass {
  private HpMessageOuterClass() {}
  public static void registerAllExtensions(
          com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
          com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
            (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface HpMessageOrBuilder extends
          // @@protoc_insertion_point(interface_extends:HpMessage)
          com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     *消息类型
     * </pre>
     *
     * <code>.HpMessage.HpMessageType type = 1;</code>
     * @return The enum numeric value on the wire for type.
     */
    int getTypeValue();
    /**
     * <pre>
     *消息类型
     * </pre>
     *
     * <code>.HpMessage.HpMessageType type = 1;</code>
     * @return The type.
     */
    HpMessageOuterClass.HpMessage.HpMessageType getType();

    /**
     * <pre>
     *元数据
     * </pre>
     *
     * <code>.HpMessage.MetaData metaData = 2;</code>
     * @return Whether the metaData field is set.
     */
    boolean hasMetaData();
    /**
     * <pre>
     *元数据
     * </pre>
     *
     * <code>.HpMessage.MetaData metaData = 2;</code>
     * @return The metaData.
     */
    HpMessageOuterClass.HpMessage.MetaData getMetaData();
    /**
     * <pre>
     *元数据
     * </pre>
     *
     * <code>.HpMessage.MetaData metaData = 2;</code>
     */
    HpMessageOuterClass.HpMessage.MetaDataOrBuilder getMetaDataOrBuilder();

    /**
     * <pre>
     *穿透真实数据流
     * </pre>
     *
     * <code>bytes data = 3;</code>
     * @return The data.
     */
    com.google.protobuf.ByteString getData();
  }
  /**
   * <pre>
   *生成的代码的类名和文件名
   * </pre>
   *
   * Protobuf type {@code HpMessage}
   */
  public static final class HpMessage extends
          com.google.protobuf.GeneratedMessageV3 implements
          // @@protoc_insertion_point(message_implements:HpMessage)
          HpMessageOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use HpMessage.newBuilder() to construct.
    private HpMessage(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private HpMessage() {
      type_ = 0;
      data_ = com.google.protobuf.ByteString.EMPTY;
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
            UnusedPrivateParameter unused) {
      return new HpMessage();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private HpMessage(
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
            case 8: {
              int rawValue = input.readEnum();

              type_ = rawValue;
              break;
            }
            case 18: {
              HpMessageOuterClass.HpMessage.MetaData.Builder subBuilder = null;
              if (metaData_ != null) {
                subBuilder = metaData_.toBuilder();
              }
              metaData_ = input.readMessage(HpMessageOuterClass.HpMessage.MetaData.parser(), extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(metaData_);
                metaData_ = subBuilder.buildPartial();
              }

              break;
            }
            case 26: {

              data_ = input.readBytes();
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
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(this);
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
      return HpMessageOuterClass.internal_static_HpMessage_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
    internalGetFieldAccessorTable() {
      return HpMessageOuterClass.internal_static_HpMessage_fieldAccessorTable
              .ensureFieldAccessorsInitialized(
                      HpMessageOuterClass.HpMessage.class, HpMessageOuterClass.HpMessage.Builder.class);
    }

    /**
     * <pre>
     *枚举消息类型
     * </pre>
     *
     * Protobuf enum {@code HpMessage.HpMessageType}
     */
    public enum HpMessageType
            implements com.google.protobuf.ProtocolMessageEnum {
      /**
       * <code>REGISTER = 0;</code>
       */
      REGISTER(0),
      /**
       * <code>REGISTER_RESULT = 1;</code>
       */
      REGISTER_RESULT(1),
      /**
       * <code>CONNECTED = 2;</code>
       */
      CONNECTED(2),
      /**
       * <code>DISCONNECTED = 3;</code>
       */
      DISCONNECTED(3),
      /**
       * <code>DATA = 4;</code>
       */
      DATA(4),
      /**
       * <code>KEEPALIVE = 5;</code>
       */
      KEEPALIVE(5),
      UNRECOGNIZED(-1),
      ;

      /**
       * <code>REGISTER = 0;</code>
       */
      public static final int REGISTER_VALUE = 0;
      /**
       * <code>REGISTER_RESULT = 1;</code>
       */
      public static final int REGISTER_RESULT_VALUE = 1;
      /**
       * <code>CONNECTED = 2;</code>
       */
      public static final int CONNECTED_VALUE = 2;
      /**
       * <code>DISCONNECTED = 3;</code>
       */
      public static final int DISCONNECTED_VALUE = 3;
      /**
       * <code>DATA = 4;</code>
       */
      public static final int DATA_VALUE = 4;
      /**
       * <code>KEEPALIVE = 5;</code>
       */
      public static final int KEEPALIVE_VALUE = 5;


      public final int getNumber() {
        if (this == UNRECOGNIZED) {
          throw new java.lang.IllegalArgumentException(
                  "Can't get the number of an unknown enum value.");
        }
        return value;
      }

      /**
       * @param value The numeric wire value of the corresponding enum entry.
       * @return The enum associated with the given numeric wire value.
       * @deprecated Use {@link #forNumber(int)} instead.
       */
      @java.lang.Deprecated
      public static HpMessageType valueOf(int value) {
        return forNumber(value);
      }

      /**
       * @param value The numeric wire value of the corresponding enum entry.
       * @return The enum associated with the given numeric wire value.
       */
      public static HpMessageType forNumber(int value) {
        switch (value) {
          case 0: return REGISTER;
          case 1: return REGISTER_RESULT;
          case 2: return CONNECTED;
          case 3: return DISCONNECTED;
          case 4: return DATA;
          case 5: return KEEPALIVE;
          default: return null;
        }
      }

      public static com.google.protobuf.Internal.EnumLiteMap<HpMessageType>
      internalGetValueMap() {
        return internalValueMap;
      }
      private static final com.google.protobuf.Internal.EnumLiteMap<
              HpMessageType> internalValueMap =
              new com.google.protobuf.Internal.EnumLiteMap<HpMessageType>() {
                public HpMessageType findValueByNumber(int number) {
                  return HpMessageType.forNumber(number);
                }
              };

      public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
        if (this == UNRECOGNIZED) {
          throw new java.lang.IllegalStateException(
                  "Can't get the descriptor of an unrecognized enum value.");
        }
        return getDescriptor().getValues().get(ordinal());
      }
      public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
        return getDescriptor();
      }
      public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
        return HpMessageOuterClass.HpMessage.getDescriptor().getEnumTypes().get(0);
      }

      private static final HpMessageType[] VALUES = values();

      public static HpMessageType valueOf(
              com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != getDescriptor()) {
          throw new java.lang.IllegalArgumentException(
                  "EnumValueDescriptor is not for this type.");
        }
        if (desc.getIndex() == -1) {
          return UNRECOGNIZED;
        }
        return VALUES[desc.getIndex()];
      }

      private final int value;

      private HpMessageType(int value) {
        this.value = value;
      }

      // @@protoc_insertion_point(enum_scope:HpMessage.HpMessageType)
    }

    public interface MetaDataOrBuilder extends
            // @@protoc_insertion_point(interface_extends:HpMessage.MetaData)
            com.google.protobuf.MessageOrBuilder {

      /**
       * <code>int32 port = 1;</code>
       * @return The port.
       */
      int getPort();

      /**
       * <code>string username = 2;</code>
       * @return The username.
       */
      java.lang.String getUsername();
      /**
       * <code>string username = 2;</code>
       * @return The bytes for username.
       */
      com.google.protobuf.ByteString
      getUsernameBytes();

      /**
       * <code>string password = 3;</code>
       * @return The password.
       */
      java.lang.String getPassword();
      /**
       * <code>string password = 3;</code>
       * @return The bytes for password.
       */
      com.google.protobuf.ByteString
      getPasswordBytes();

      /**
       * <code>string channelId = 4;</code>
       * @return The channelId.
       */
      java.lang.String getChannelId();
      /**
       * <code>string channelId = 4;</code>
       * @return The bytes for channelId.
       */
      com.google.protobuf.ByteString
      getChannelIdBytes();

      /**
       * <code>bool success = 5;</code>
       * @return The success.
       */
      boolean getSuccess();

      /**
       * <code>string reason = 6;</code>
       * @return The reason.
       */
      java.lang.String getReason();
      /**
       * <code>string reason = 6;</code>
       * @return The bytes for reason.
       */
      com.google.protobuf.ByteString
      getReasonBytes();
    }
    /**
     * Protobuf type {@code HpMessage.MetaData}
     */
    public static final class MetaData extends
            com.google.protobuf.GeneratedMessageV3 implements
            // @@protoc_insertion_point(message_implements:HpMessage.MetaData)
            MetaDataOrBuilder {
      private static final long serialVersionUID = 0L;
      // Use MetaData.newBuilder() to construct.
      private MetaData(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
      }
      private MetaData() {
        username_ = "";
        password_ = "";
        channelId_ = "";
        reason_ = "";
      }

      @java.lang.Override
      @SuppressWarnings({"unused"})
      protected java.lang.Object newInstance(
              UnusedPrivateParameter unused) {
        return new MetaData();
      }

      @java.lang.Override
      public final com.google.protobuf.UnknownFieldSet
      getUnknownFields() {
        return this.unknownFields;
      }
      private MetaData(
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
              case 8: {

                port_ = input.readInt32();
                break;
              }
              case 18: {
                java.lang.String s = input.readStringRequireUtf8();

                username_ = s;
                break;
              }
              case 26: {
                java.lang.String s = input.readStringRequireUtf8();

                password_ = s;
                break;
              }
              case 34: {
                java.lang.String s = input.readStringRequireUtf8();

                channelId_ = s;
                break;
              }
              case 40: {

                success_ = input.readBool();
                break;
              }
              case 50: {
                java.lang.String s = input.readStringRequireUtf8();

                reason_ = s;
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
        } catch (com.google.protobuf.UninitializedMessageException e) {
          throw e.asInvalidProtocolBufferException().setUnfinishedMessage(this);
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
        return HpMessageOuterClass.internal_static_HpMessage_MetaData_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
        return HpMessageOuterClass.internal_static_HpMessage_MetaData_fieldAccessorTable
                .ensureFieldAccessorsInitialized(
                        HpMessageOuterClass.HpMessage.MetaData.class, HpMessageOuterClass.HpMessage.MetaData.Builder.class);
      }

      public static final int PORT_FIELD_NUMBER = 1;
      private int port_;
      /**
       * <code>int32 port = 1;</code>
       * @return The port.
       */
      @java.lang.Override
      public int getPort() {
        return port_;
      }

      public static final int USERNAME_FIELD_NUMBER = 2;
      private volatile java.lang.Object username_;
      /**
       * <code>string username = 2;</code>
       * @return The username.
       */
      @java.lang.Override
      public java.lang.String getUsername() {
        java.lang.Object ref = username_;
        if (ref instanceof java.lang.String) {
          return (java.lang.String) ref;
        } else {
          com.google.protobuf.ByteString bs =
                  (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          username_ = s;
          return s;
        }
      }
      /**
       * <code>string username = 2;</code>
       * @return The bytes for username.
       */
      @java.lang.Override
      public com.google.protobuf.ByteString
      getUsernameBytes() {
        java.lang.Object ref = username_;
        if (ref instanceof java.lang.String) {
          com.google.protobuf.ByteString b =
                  com.google.protobuf.ByteString.copyFromUtf8(
                          (java.lang.String) ref);
          username_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }

      public static final int PASSWORD_FIELD_NUMBER = 3;
      private volatile java.lang.Object password_;
      /**
       * <code>string password = 3;</code>
       * @return The password.
       */
      @java.lang.Override
      public java.lang.String getPassword() {
        java.lang.Object ref = password_;
        if (ref instanceof java.lang.String) {
          return (java.lang.String) ref;
        } else {
          com.google.protobuf.ByteString bs =
                  (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          password_ = s;
          return s;
        }
      }
      /**
       * <code>string password = 3;</code>
       * @return The bytes for password.
       */
      @java.lang.Override
      public com.google.protobuf.ByteString
      getPasswordBytes() {
        java.lang.Object ref = password_;
        if (ref instanceof java.lang.String) {
          com.google.protobuf.ByteString b =
                  com.google.protobuf.ByteString.copyFromUtf8(
                          (java.lang.String) ref);
          password_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }

      public static final int CHANNELID_FIELD_NUMBER = 4;
      private volatile java.lang.Object channelId_;
      /**
       * <code>string channelId = 4;</code>
       * @return The channelId.
       */
      @java.lang.Override
      public java.lang.String getChannelId() {
        java.lang.Object ref = channelId_;
        if (ref instanceof java.lang.String) {
          return (java.lang.String) ref;
        } else {
          com.google.protobuf.ByteString bs =
                  (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          channelId_ = s;
          return s;
        }
      }
      /**
       * <code>string channelId = 4;</code>
       * @return The bytes for channelId.
       */
      @java.lang.Override
      public com.google.protobuf.ByteString
      getChannelIdBytes() {
        java.lang.Object ref = channelId_;
        if (ref instanceof java.lang.String) {
          com.google.protobuf.ByteString b =
                  com.google.protobuf.ByteString.copyFromUtf8(
                          (java.lang.String) ref);
          channelId_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }

      public static final int SUCCESS_FIELD_NUMBER = 5;
      private boolean success_;
      /**
       * <code>bool success = 5;</code>
       * @return The success.
       */
      @java.lang.Override
      public boolean getSuccess() {
        return success_;
      }

      public static final int REASON_FIELD_NUMBER = 6;
      private volatile java.lang.Object reason_;
      /**
       * <code>string reason = 6;</code>
       * @return The reason.
       */
      @java.lang.Override
      public java.lang.String getReason() {
        java.lang.Object ref = reason_;
        if (ref instanceof java.lang.String) {
          return (java.lang.String) ref;
        } else {
          com.google.protobuf.ByteString bs =
                  (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          reason_ = s;
          return s;
        }
      }
      /**
       * <code>string reason = 6;</code>
       * @return The bytes for reason.
       */
      @java.lang.Override
      public com.google.protobuf.ByteString
      getReasonBytes() {
        java.lang.Object ref = reason_;
        if (ref instanceof java.lang.String) {
          com.google.protobuf.ByteString b =
                  com.google.protobuf.ByteString.copyFromUtf8(
                          (java.lang.String) ref);
          reason_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
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
        if (port_ != 0) {
          output.writeInt32(1, port_);
        }
        if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(username_)) {
          com.google.protobuf.GeneratedMessageV3.writeString(output, 2, username_);
        }
        if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(password_)) {
          com.google.protobuf.GeneratedMessageV3.writeString(output, 3, password_);
        }
        if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(channelId_)) {
          com.google.protobuf.GeneratedMessageV3.writeString(output, 4, channelId_);
        }
        if (success_ != false) {
          output.writeBool(5, success_);
        }
        if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(reason_)) {
          com.google.protobuf.GeneratedMessageV3.writeString(output, 6, reason_);
        }
        unknownFields.writeTo(output);
      }

      @java.lang.Override
      public int getSerializedSize() {
        int size = memoizedSize;
        if (size != -1) return size;

        size = 0;
        if (port_ != 0) {
          size += com.google.protobuf.CodedOutputStream
                  .computeInt32Size(1, port_);
        }
        if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(username_)) {
          size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, username_);
        }
        if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(password_)) {
          size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, password_);
        }
        if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(channelId_)) {
          size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, channelId_);
        }
        if (success_ != false) {
          size += com.google.protobuf.CodedOutputStream
                  .computeBoolSize(5, success_);
        }
        if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(reason_)) {
          size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, reason_);
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
        if (!(obj instanceof HpMessageOuterClass.HpMessage.MetaData)) {
          return super.equals(obj);
        }
        HpMessageOuterClass.HpMessage.MetaData other = (HpMessageOuterClass.HpMessage.MetaData) obj;

        if (getPort()
                != other.getPort()) return false;
        if (!getUsername()
                .equals(other.getUsername())) return false;
        if (!getPassword()
                .equals(other.getPassword())) return false;
        if (!getChannelId()
                .equals(other.getChannelId())) return false;
        if (getSuccess()
                != other.getSuccess()) return false;
        if (!getReason()
                .equals(other.getReason())) return false;
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
        hash = (37 * hash) + PORT_FIELD_NUMBER;
        hash = (53 * hash) + getPort();
        hash = (37 * hash) + USERNAME_FIELD_NUMBER;
        hash = (53 * hash) + getUsername().hashCode();
        hash = (37 * hash) + PASSWORD_FIELD_NUMBER;
        hash = (53 * hash) + getPassword().hashCode();
        hash = (37 * hash) + CHANNELID_FIELD_NUMBER;
        hash = (53 * hash) + getChannelId().hashCode();
        hash = (37 * hash) + SUCCESS_FIELD_NUMBER;
        hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
                getSuccess());
        hash = (37 * hash) + REASON_FIELD_NUMBER;
        hash = (53 * hash) + getReason().hashCode();
        hash = (29 * hash) + unknownFields.hashCode();
        memoizedHashCode = hash;
        return hash;
      }

      public static HpMessageOuterClass.HpMessage.MetaData parseFrom(
              java.nio.ByteBuffer data)
              throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
      }
      public static HpMessageOuterClass.HpMessage.MetaData parseFrom(
              java.nio.ByteBuffer data,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
      }
      public static HpMessageOuterClass.HpMessage.MetaData parseFrom(
              com.google.protobuf.ByteString data)
              throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
      }
      public static HpMessageOuterClass.HpMessage.MetaData parseFrom(
              com.google.protobuf.ByteString data,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
      }
      public static HpMessageOuterClass.HpMessage.MetaData parseFrom(byte[] data)
              throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
      }
      public static HpMessageOuterClass.HpMessage.MetaData parseFrom(
              byte[] data,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
      }
      public static HpMessageOuterClass.HpMessage.MetaData parseFrom(java.io.InputStream input)
              throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input);
      }
      public static HpMessageOuterClass.HpMessage.MetaData parseFrom(
              java.io.InputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input, extensionRegistry);
      }
      public static HpMessageOuterClass.HpMessage.MetaData parseDelimitedFrom(java.io.InputStream input)
              throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseDelimitedWithIOException(PARSER, input);
      }
      public static HpMessageOuterClass.HpMessage.MetaData parseDelimitedFrom(
              java.io.InputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
      }
      public static HpMessageOuterClass.HpMessage.MetaData parseFrom(
              com.google.protobuf.CodedInputStream input)
              throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input);
      }
      public static HpMessageOuterClass.HpMessage.MetaData parseFrom(
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
      public static Builder newBuilder(HpMessageOuterClass.HpMessage.MetaData prototype) {
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
       * Protobuf type {@code HpMessage.MetaData}
       */
      public static final class Builder extends
              com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
              // @@protoc_insertion_point(builder_implements:HpMessage.MetaData)
              HpMessageOuterClass.HpMessage.MetaDataOrBuilder {
        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
          return HpMessageOuterClass.internal_static_HpMessage_MetaData_descriptor;
        }

        @java.lang.Override
        protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
          return HpMessageOuterClass.internal_static_HpMessage_MetaData_fieldAccessorTable
                  .ensureFieldAccessorsInitialized(
                          HpMessageOuterClass.HpMessage.MetaData.class, HpMessageOuterClass.HpMessage.MetaData.Builder.class);
        }

        // Construct using HpMessageOuterClass.HpMessage.MetaData.newBuilder()
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
          port_ = 0;

          username_ = "";

          password_ = "";

          channelId_ = "";

          success_ = false;

          reason_ = "";

          return this;
        }

        @java.lang.Override
        public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
          return HpMessageOuterClass.internal_static_HpMessage_MetaData_descriptor;
        }

        @java.lang.Override
        public HpMessageOuterClass.HpMessage.MetaData getDefaultInstanceForType() {
          return HpMessageOuterClass.HpMessage.MetaData.getDefaultInstance();
        }

        @java.lang.Override
        public HpMessageOuterClass.HpMessage.MetaData build() {
          HpMessageOuterClass.HpMessage.MetaData result = buildPartial();
          if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
          }
          return result;
        }

        @java.lang.Override
        public HpMessageOuterClass.HpMessage.MetaData buildPartial() {
          HpMessageOuterClass.HpMessage.MetaData result = new HpMessageOuterClass.HpMessage.MetaData(this);
          result.port_ = port_;
          result.username_ = username_;
          result.password_ = password_;
          result.channelId_ = channelId_;
          result.success_ = success_;
          result.reason_ = reason_;
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
          if (other instanceof HpMessageOuterClass.HpMessage.MetaData) {
            return mergeFrom((HpMessageOuterClass.HpMessage.MetaData)other);
          } else {
            super.mergeFrom(other);
            return this;
          }
        }

        public Builder mergeFrom(HpMessageOuterClass.HpMessage.MetaData other) {
          if (other == HpMessageOuterClass.HpMessage.MetaData.getDefaultInstance()) return this;
          if (other.getPort() != 0) {
            setPort(other.getPort());
          }
          if (!other.getUsername().isEmpty()) {
            username_ = other.username_;
            onChanged();
          }
          if (!other.getPassword().isEmpty()) {
            password_ = other.password_;
            onChanged();
          }
          if (!other.getChannelId().isEmpty()) {
            channelId_ = other.channelId_;
            onChanged();
          }
          if (other.getSuccess() != false) {
            setSuccess(other.getSuccess());
          }
          if (!other.getReason().isEmpty()) {
            reason_ = other.reason_;
            onChanged();
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
          HpMessageOuterClass.HpMessage.MetaData parsedMessage = null;
          try {
            parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
          } catch (com.google.protobuf.InvalidProtocolBufferException e) {
            parsedMessage = (HpMessageOuterClass.HpMessage.MetaData) e.getUnfinishedMessage();
            throw e.unwrapIOException();
          } finally {
            if (parsedMessage != null) {
              mergeFrom(parsedMessage);
            }
          }
          return this;
        }

        private int port_ ;
        /**
         * <code>int32 port = 1;</code>
         * @return The port.
         */
        @java.lang.Override
        public int getPort() {
          return port_;
        }
        /**
         * <code>int32 port = 1;</code>
         * @param value The port to set.
         * @return This builder for chaining.
         */
        public Builder setPort(int value) {

          port_ = value;
          onChanged();
          return this;
        }
        /**
         * <code>int32 port = 1;</code>
         * @return This builder for chaining.
         */
        public Builder clearPort() {

          port_ = 0;
          onChanged();
          return this;
        }

        private java.lang.Object username_ = "";
        /**
         * <code>string username = 2;</code>
         * @return The username.
         */
        public java.lang.String getUsername() {
          java.lang.Object ref = username_;
          if (!(ref instanceof java.lang.String)) {
            com.google.protobuf.ByteString bs =
                    (com.google.protobuf.ByteString) ref;
            java.lang.String s = bs.toStringUtf8();
            username_ = s;
            return s;
          } else {
            return (java.lang.String) ref;
          }
        }
        /**
         * <code>string username = 2;</code>
         * @return The bytes for username.
         */
        public com.google.protobuf.ByteString
        getUsernameBytes() {
          java.lang.Object ref = username_;
          if (ref instanceof String) {
            com.google.protobuf.ByteString b =
                    com.google.protobuf.ByteString.copyFromUtf8(
                            (java.lang.String) ref);
            username_ = b;
            return b;
          } else {
            return (com.google.protobuf.ByteString) ref;
          }
        }
        /**
         * <code>string username = 2;</code>
         * @param value The username to set.
         * @return This builder for chaining.
         */
        public Builder setUsername(
                java.lang.String value) {
          if (value == null) {
            throw new NullPointerException();
          }

          username_ = value;
          onChanged();
          return this;
        }
        /**
         * <code>string username = 2;</code>
         * @return This builder for chaining.
         */
        public Builder clearUsername() {

          username_ = getDefaultInstance().getUsername();
          onChanged();
          return this;
        }
        /**
         * <code>string username = 2;</code>
         * @param value The bytes for username to set.
         * @return This builder for chaining.
         */
        public Builder setUsernameBytes(
                com.google.protobuf.ByteString value) {
          if (value == null) {
            throw new NullPointerException();
          }
          checkByteStringIsUtf8(value);

          username_ = value;
          onChanged();
          return this;
        }

        private java.lang.Object password_ = "";
        /**
         * <code>string password = 3;</code>
         * @return The password.
         */
        public java.lang.String getPassword() {
          java.lang.Object ref = password_;
          if (!(ref instanceof java.lang.String)) {
            com.google.protobuf.ByteString bs =
                    (com.google.protobuf.ByteString) ref;
            java.lang.String s = bs.toStringUtf8();
            password_ = s;
            return s;
          } else {
            return (java.lang.String) ref;
          }
        }
        /**
         * <code>string password = 3;</code>
         * @return The bytes for password.
         */
        public com.google.protobuf.ByteString
        getPasswordBytes() {
          java.lang.Object ref = password_;
          if (ref instanceof String) {
            com.google.protobuf.ByteString b =
                    com.google.protobuf.ByteString.copyFromUtf8(
                            (java.lang.String) ref);
            password_ = b;
            return b;
          } else {
            return (com.google.protobuf.ByteString) ref;
          }
        }
        /**
         * <code>string password = 3;</code>
         * @param value The password to set.
         * @return This builder for chaining.
         */
        public Builder setPassword(
                java.lang.String value) {
          if (value == null) {
            throw new NullPointerException();
          }

          password_ = value;
          onChanged();
          return this;
        }
        /**
         * <code>string password = 3;</code>
         * @return This builder for chaining.
         */
        public Builder clearPassword() {

          password_ = getDefaultInstance().getPassword();
          onChanged();
          return this;
        }
        /**
         * <code>string password = 3;</code>
         * @param value The bytes for password to set.
         * @return This builder for chaining.
         */
        public Builder setPasswordBytes(
                com.google.protobuf.ByteString value) {
          if (value == null) {
            throw new NullPointerException();
          }
          checkByteStringIsUtf8(value);

          password_ = value;
          onChanged();
          return this;
        }

        private java.lang.Object channelId_ = "";
        /**
         * <code>string channelId = 4;</code>
         * @return The channelId.
         */
        public java.lang.String getChannelId() {
          java.lang.Object ref = channelId_;
          if (!(ref instanceof java.lang.String)) {
            com.google.protobuf.ByteString bs =
                    (com.google.protobuf.ByteString) ref;
            java.lang.String s = bs.toStringUtf8();
            channelId_ = s;
            return s;
          } else {
            return (java.lang.String) ref;
          }
        }
        /**
         * <code>string channelId = 4;</code>
         * @return The bytes for channelId.
         */
        public com.google.protobuf.ByteString
        getChannelIdBytes() {
          java.lang.Object ref = channelId_;
          if (ref instanceof String) {
            com.google.protobuf.ByteString b =
                    com.google.protobuf.ByteString.copyFromUtf8(
                            (java.lang.String) ref);
            channelId_ = b;
            return b;
          } else {
            return (com.google.protobuf.ByteString) ref;
          }
        }
        /**
         * <code>string channelId = 4;</code>
         * @param value The channelId to set.
         * @return This builder for chaining.
         */
        public Builder setChannelId(
                java.lang.String value) {
          if (value == null) {
            throw new NullPointerException();
          }

          channelId_ = value;
          onChanged();
          return this;
        }
        /**
         * <code>string channelId = 4;</code>
         * @return This builder for chaining.
         */
        public Builder clearChannelId() {

          channelId_ = getDefaultInstance().getChannelId();
          onChanged();
          return this;
        }
        /**
         * <code>string channelId = 4;</code>
         * @param value The bytes for channelId to set.
         * @return This builder for chaining.
         */
        public Builder setChannelIdBytes(
                com.google.protobuf.ByteString value) {
          if (value == null) {
            throw new NullPointerException();
          }
          checkByteStringIsUtf8(value);

          channelId_ = value;
          onChanged();
          return this;
        }

        private boolean success_ ;
        /**
         * <code>bool success = 5;</code>
         * @return The success.
         */
        @java.lang.Override
        public boolean getSuccess() {
          return success_;
        }
        /**
         * <code>bool success = 5;</code>
         * @param value The success to set.
         * @return This builder for chaining.
         */
        public Builder setSuccess(boolean value) {

          success_ = value;
          onChanged();
          return this;
        }
        /**
         * <code>bool success = 5;</code>
         * @return This builder for chaining.
         */
        public Builder clearSuccess() {

          success_ = false;
          onChanged();
          return this;
        }

        private java.lang.Object reason_ = "";
        /**
         * <code>string reason = 6;</code>
         * @return The reason.
         */
        public java.lang.String getReason() {
          java.lang.Object ref = reason_;
          if (!(ref instanceof java.lang.String)) {
            com.google.protobuf.ByteString bs =
                    (com.google.protobuf.ByteString) ref;
            java.lang.String s = bs.toStringUtf8();
            reason_ = s;
            return s;
          } else {
            return (java.lang.String) ref;
          }
        }
        /**
         * <code>string reason = 6;</code>
         * @return The bytes for reason.
         */
        public com.google.protobuf.ByteString
        getReasonBytes() {
          java.lang.Object ref = reason_;
          if (ref instanceof String) {
            com.google.protobuf.ByteString b =
                    com.google.protobuf.ByteString.copyFromUtf8(
                            (java.lang.String) ref);
            reason_ = b;
            return b;
          } else {
            return (com.google.protobuf.ByteString) ref;
          }
        }
        /**
         * <code>string reason = 6;</code>
         * @param value The reason to set.
         * @return This builder for chaining.
         */
        public Builder setReason(
                java.lang.String value) {
          if (value == null) {
            throw new NullPointerException();
          }

          reason_ = value;
          onChanged();
          return this;
        }
        /**
         * <code>string reason = 6;</code>
         * @return This builder for chaining.
         */
        public Builder clearReason() {

          reason_ = getDefaultInstance().getReason();
          onChanged();
          return this;
        }
        /**
         * <code>string reason = 6;</code>
         * @param value The bytes for reason to set.
         * @return This builder for chaining.
         */
        public Builder setReasonBytes(
                com.google.protobuf.ByteString value) {
          if (value == null) {
            throw new NullPointerException();
          }
          checkByteStringIsUtf8(value);

          reason_ = value;
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


        // @@protoc_insertion_point(builder_scope:HpMessage.MetaData)
      }

      // @@protoc_insertion_point(class_scope:HpMessage.MetaData)
      private static final HpMessageOuterClass.HpMessage.MetaData DEFAULT_INSTANCE;
      static {
        DEFAULT_INSTANCE = new HpMessageOuterClass.HpMessage.MetaData();
      }

      public static HpMessageOuterClass.HpMessage.MetaData getDefaultInstance() {
        return DEFAULT_INSTANCE;
      }

      private static final com.google.protobuf.Parser<MetaData>
              PARSER = new com.google.protobuf.AbstractParser<MetaData>() {
        @java.lang.Override
        public MetaData parsePartialFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
          return new MetaData(input, extensionRegistry);
        }
      };

      public static com.google.protobuf.Parser<MetaData> parser() {
        return PARSER;
      }

      @java.lang.Override
      public com.google.protobuf.Parser<MetaData> getParserForType() {
        return PARSER;
      }

      @java.lang.Override
      public HpMessageOuterClass.HpMessage.MetaData getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
      }

    }

    public static final int TYPE_FIELD_NUMBER = 1;
    private int type_;
    /**
     * <pre>
     *消息类型
     * </pre>
     *
     * <code>.HpMessage.HpMessageType type = 1;</code>
     * @return The enum numeric value on the wire for type.
     */
    @java.lang.Override public int getTypeValue() {
      return type_;
    }
    /**
     * <pre>
     *消息类型
     * </pre>
     *
     * <code>.HpMessage.HpMessageType type = 1;</code>
     * @return The type.
     */
    @java.lang.Override public HpMessageOuterClass.HpMessage.HpMessageType getType() {
      @SuppressWarnings("deprecation")
      HpMessageOuterClass.HpMessage.HpMessageType result = HpMessageOuterClass.HpMessage.HpMessageType.valueOf(type_);
      return result == null ? HpMessageOuterClass.HpMessage.HpMessageType.UNRECOGNIZED : result;
    }

    public static final int METADATA_FIELD_NUMBER = 2;
    private HpMessageOuterClass.HpMessage.MetaData metaData_;
    /**
     * <pre>
     *元数据
     * </pre>
     *
     * <code>.HpMessage.MetaData metaData = 2;</code>
     * @return Whether the metaData field is set.
     */
    @java.lang.Override
    public boolean hasMetaData() {
      return metaData_ != null;
    }
    /**
     * <pre>
     *元数据
     * </pre>
     *
     * <code>.HpMessage.MetaData metaData = 2;</code>
     * @return The metaData.
     */
    @java.lang.Override
    public HpMessageOuterClass.HpMessage.MetaData getMetaData() {
      return metaData_ == null ? HpMessageOuterClass.HpMessage.MetaData.getDefaultInstance() : metaData_;
    }
    /**
     * <pre>
     *元数据
     * </pre>
     *
     * <code>.HpMessage.MetaData metaData = 2;</code>
     */
    @java.lang.Override
    public HpMessageOuterClass.HpMessage.MetaDataOrBuilder getMetaDataOrBuilder() {
      return getMetaData();
    }

    public static final int DATA_FIELD_NUMBER = 3;
    private com.google.protobuf.ByteString data_;
    /**
     * <pre>
     *穿透真实数据流
     * </pre>
     *
     * <code>bytes data = 3;</code>
     * @return The data.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getData() {
      return data_;
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
      if (type_ != HpMessageOuterClass.HpMessage.HpMessageType.REGISTER.getNumber()) {
        output.writeEnum(1, type_);
      }
      if (metaData_ != null) {
        output.writeMessage(2, getMetaData());
      }
      if (!data_.isEmpty()) {
        output.writeBytes(3, data_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (type_ != HpMessageOuterClass.HpMessage.HpMessageType.REGISTER.getNumber()) {
        size += com.google.protobuf.CodedOutputStream
                .computeEnumSize(1, type_);
      }
      if (metaData_ != null) {
        size += com.google.protobuf.CodedOutputStream
                .computeMessageSize(2, getMetaData());
      }
      if (!data_.isEmpty()) {
        size += com.google.protobuf.CodedOutputStream
                .computeBytesSize(3, data_);
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
      if (!(obj instanceof HpMessageOuterClass.HpMessage)) {
        return super.equals(obj);
      }
      HpMessageOuterClass.HpMessage other = (HpMessageOuterClass.HpMessage) obj;

      if (type_ != other.type_) return false;
      if (hasMetaData() != other.hasMetaData()) return false;
      if (hasMetaData()) {
        if (!getMetaData()
                .equals(other.getMetaData())) return false;
      }
      if (!getData()
              .equals(other.getData())) return false;
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
      hash = (37 * hash) + TYPE_FIELD_NUMBER;
      hash = (53 * hash) + type_;
      if (hasMetaData()) {
        hash = (37 * hash) + METADATA_FIELD_NUMBER;
        hash = (53 * hash) + getMetaData().hashCode();
      }
      hash = (37 * hash) + DATA_FIELD_NUMBER;
      hash = (53 * hash) + getData().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static HpMessageOuterClass.HpMessage parseFrom(
            java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static HpMessageOuterClass.HpMessage parseFrom(
            java.nio.ByteBuffer data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static HpMessageOuterClass.HpMessage parseFrom(
            com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static HpMessageOuterClass.HpMessage parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static HpMessageOuterClass.HpMessage parseFrom(byte[] data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static HpMessageOuterClass.HpMessage parseFrom(
            byte[] data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static HpMessageOuterClass.HpMessage parseFrom(java.io.InputStream input)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
              .parseWithIOException(PARSER, input);
    }
    public static HpMessageOuterClass.HpMessage parseFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
              .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static HpMessageOuterClass.HpMessage parseDelimitedFrom(java.io.InputStream input)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
              .parseDelimitedWithIOException(PARSER, input);
    }
    public static HpMessageOuterClass.HpMessage parseDelimitedFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
              .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static HpMessageOuterClass.HpMessage parseFrom(
            com.google.protobuf.CodedInputStream input)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
              .parseWithIOException(PARSER, input);
    }
    public static HpMessageOuterClass.HpMessage parseFrom(
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
    public static Builder newBuilder(HpMessageOuterClass.HpMessage prototype) {
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
     * <pre>
     *生成的代码的类名和文件名
     * </pre>
     *
     * Protobuf type {@code HpMessage}
     */
    public static final class Builder extends
            com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
            // @@protoc_insertion_point(builder_implements:HpMessage)
            HpMessageOuterClass.HpMessageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
        return HpMessageOuterClass.internal_static_HpMessage_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
        return HpMessageOuterClass.internal_static_HpMessage_fieldAccessorTable
                .ensureFieldAccessorsInitialized(
                        HpMessageOuterClass.HpMessage.class, HpMessageOuterClass.HpMessage.Builder.class);
      }

      // Construct using HpMessageOuterClass.HpMessage.newBuilder()
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
        type_ = 0;

        if (metaDataBuilder_ == null) {
          metaData_ = null;
        } else {
          metaData_ = null;
          metaDataBuilder_ = null;
        }
        data_ = com.google.protobuf.ByteString.EMPTY;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
      getDescriptorForType() {
        return HpMessageOuterClass.internal_static_HpMessage_descriptor;
      }

      @java.lang.Override
      public HpMessageOuterClass.HpMessage getDefaultInstanceForType() {
        return HpMessageOuterClass.HpMessage.getDefaultInstance();
      }

      @java.lang.Override
      public HpMessageOuterClass.HpMessage build() {
        HpMessageOuterClass.HpMessage result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public HpMessageOuterClass.HpMessage buildPartial() {
        HpMessageOuterClass.HpMessage result = new HpMessageOuterClass.HpMessage(this);
        result.type_ = type_;
        if (metaDataBuilder_ == null) {
          result.metaData_ = metaData_;
        } else {
          result.metaData_ = metaDataBuilder_.build();
        }
        result.data_ = data_;
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
        if (other instanceof HpMessageOuterClass.HpMessage) {
          return mergeFrom((HpMessageOuterClass.HpMessage)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(HpMessageOuterClass.HpMessage other) {
        if (other == HpMessageOuterClass.HpMessage.getDefaultInstance()) return this;
        if (other.type_ != 0) {
          setTypeValue(other.getTypeValue());
        }
        if (other.hasMetaData()) {
          mergeMetaData(other.getMetaData());
        }
        if (other.getData() != com.google.protobuf.ByteString.EMPTY) {
          setData(other.getData());
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
        HpMessageOuterClass.HpMessage parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (HpMessageOuterClass.HpMessage) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int type_ = 0;
      /**
       * <pre>
       *消息类型
       * </pre>
       *
       * <code>.HpMessage.HpMessageType type = 1;</code>
       * @return The enum numeric value on the wire for type.
       */
      @java.lang.Override public int getTypeValue() {
        return type_;
      }
      /**
       * <pre>
       *消息类型
       * </pre>
       *
       * <code>.HpMessage.HpMessageType type = 1;</code>
       * @param value The enum numeric value on the wire for type to set.
       * @return This builder for chaining.
       */
      public Builder setTypeValue(int value) {

        type_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *消息类型
       * </pre>
       *
       * <code>.HpMessage.HpMessageType type = 1;</code>
       * @return The type.
       */
      @java.lang.Override
      public HpMessageOuterClass.HpMessage.HpMessageType getType() {
        @SuppressWarnings("deprecation")
        HpMessageOuterClass.HpMessage.HpMessageType result = HpMessageOuterClass.HpMessage.HpMessageType.valueOf(type_);
        return result == null ? HpMessageOuterClass.HpMessage.HpMessageType.UNRECOGNIZED : result;
      }
      /**
       * <pre>
       *消息类型
       * </pre>
       *
       * <code>.HpMessage.HpMessageType type = 1;</code>
       * @param value The type to set.
       * @return This builder for chaining.
       */
      public Builder setType(HpMessageOuterClass.HpMessage.HpMessageType value) {
        if (value == null) {
          throw new NullPointerException();
        }

        type_ = value.getNumber();
        onChanged();
        return this;
      }
      /**
       * <pre>
       *消息类型
       * </pre>
       *
       * <code>.HpMessage.HpMessageType type = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearType() {

        type_ = 0;
        onChanged();
        return this;
      }

      private HpMessageOuterClass.HpMessage.MetaData metaData_;
      private com.google.protobuf.SingleFieldBuilderV3<
              HpMessageOuterClass.HpMessage.MetaData, HpMessageOuterClass.HpMessage.MetaData.Builder, HpMessageOuterClass.HpMessage.MetaDataOrBuilder> metaDataBuilder_;
      /**
       * <pre>
       *元数据
       * </pre>
       *
       * <code>.HpMessage.MetaData metaData = 2;</code>
       * @return Whether the metaData field is set.
       */
      public boolean hasMetaData() {
        return metaDataBuilder_ != null || metaData_ != null;
      }
      /**
       * <pre>
       *元数据
       * </pre>
       *
       * <code>.HpMessage.MetaData metaData = 2;</code>
       * @return The metaData.
       */
      public HpMessageOuterClass.HpMessage.MetaData getMetaData() {
        if (metaDataBuilder_ == null) {
          return metaData_ == null ? HpMessageOuterClass.HpMessage.MetaData.getDefaultInstance() : metaData_;
        } else {
          return metaDataBuilder_.getMessage();
        }
      }
      /**
       * <pre>
       *元数据
       * </pre>
       *
       * <code>.HpMessage.MetaData metaData = 2;</code>
       */
      public Builder setMetaData(HpMessageOuterClass.HpMessage.MetaData value) {
        if (metaDataBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          metaData_ = value;
          onChanged();
        } else {
          metaDataBuilder_.setMessage(value);
        }

        return this;
      }
      /**
       * <pre>
       *元数据
       * </pre>
       *
       * <code>.HpMessage.MetaData metaData = 2;</code>
       */
      public Builder setMetaData(
              HpMessageOuterClass.HpMessage.MetaData.Builder builderForValue) {
        if (metaDataBuilder_ == null) {
          metaData_ = builderForValue.build();
          onChanged();
        } else {
          metaDataBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /**
       * <pre>
       *元数据
       * </pre>
       *
       * <code>.HpMessage.MetaData metaData = 2;</code>
       */
      public Builder mergeMetaData(HpMessageOuterClass.HpMessage.MetaData value) {
        if (metaDataBuilder_ == null) {
          if (metaData_ != null) {
            metaData_ =
                    HpMessageOuterClass.HpMessage.MetaData.newBuilder(metaData_).mergeFrom(value).buildPartial();
          } else {
            metaData_ = value;
          }
          onChanged();
        } else {
          metaDataBuilder_.mergeFrom(value);
        }

        return this;
      }
      /**
       * <pre>
       *元数据
       * </pre>
       *
       * <code>.HpMessage.MetaData metaData = 2;</code>
       */
      public Builder clearMetaData() {
        if (metaDataBuilder_ == null) {
          metaData_ = null;
          onChanged();
        } else {
          metaData_ = null;
          metaDataBuilder_ = null;
        }

        return this;
      }
      /**
       * <pre>
       *元数据
       * </pre>
       *
       * <code>.HpMessage.MetaData metaData = 2;</code>
       */
      public HpMessageOuterClass.HpMessage.MetaData.Builder getMetaDataBuilder() {

        onChanged();
        return getMetaDataFieldBuilder().getBuilder();
      }
      /**
       * <pre>
       *元数据
       * </pre>
       *
       * <code>.HpMessage.MetaData metaData = 2;</code>
       */
      public HpMessageOuterClass.HpMessage.MetaDataOrBuilder getMetaDataOrBuilder() {
        if (metaDataBuilder_ != null) {
          return metaDataBuilder_.getMessageOrBuilder();
        } else {
          return metaData_ == null ?
                  HpMessageOuterClass.HpMessage.MetaData.getDefaultInstance() : metaData_;
        }
      }
      /**
       * <pre>
       *元数据
       * </pre>
       *
       * <code>.HpMessage.MetaData metaData = 2;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
              HpMessageOuterClass.HpMessage.MetaData, HpMessageOuterClass.HpMessage.MetaData.Builder, HpMessageOuterClass.HpMessage.MetaDataOrBuilder>
      getMetaDataFieldBuilder() {
        if (metaDataBuilder_ == null) {
          metaDataBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
                  HpMessageOuterClass.HpMessage.MetaData, HpMessageOuterClass.HpMessage.MetaData.Builder, HpMessageOuterClass.HpMessage.MetaDataOrBuilder>(
                  getMetaData(),
                  getParentForChildren(),
                  isClean());
          metaData_ = null;
        }
        return metaDataBuilder_;
      }

      private com.google.protobuf.ByteString data_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <pre>
       *穿透真实数据流
       * </pre>
       *
       * <code>bytes data = 3;</code>
       * @return The data.
       */
      @java.lang.Override
      public com.google.protobuf.ByteString getData() {
        return data_;
      }
      /**
       * <pre>
       *穿透真实数据流
       * </pre>
       *
       * <code>bytes data = 3;</code>
       * @param value The data to set.
       * @return This builder for chaining.
       */
      public Builder setData(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }

        data_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *穿透真实数据流
       * </pre>
       *
       * <code>bytes data = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearData() {

        data_ = getDefaultInstance().getData();
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


      // @@protoc_insertion_point(builder_scope:HpMessage)
    }

    // @@protoc_insertion_point(class_scope:HpMessage)
    private static final HpMessageOuterClass.HpMessage DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new HpMessageOuterClass.HpMessage();
    }

    public static HpMessageOuterClass.HpMessage getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<HpMessage>
            PARSER = new com.google.protobuf.AbstractParser<HpMessage>() {
      @java.lang.Override
      public HpMessage parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
        return new HpMessage(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<HpMessage> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<HpMessage> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public HpMessageOuterClass.HpMessage getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
          internal_static_HpMessage_descriptor;
  private static final
  com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internal_static_HpMessage_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
          internal_static_HpMessage_MetaData_descriptor;
  private static final
  com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internal_static_HpMessage_MetaData_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
  getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
          descriptor;
  static {
    java.lang.String[] descriptorData = {
            "\n\017HpMessage.proto\"\310\002\n\tHpMessage\022&\n\004type\030" +
                    "\001 \001(\0162\030.HpMessage.HpMessageType\022%\n\010metaD" +
                    "ata\030\002 \001(\0132\023.HpMessage.MetaData\022\014\n\004data\030\003" +
                    " \001(\014\032p\n\010MetaData\022\014\n\004port\030\001 \001(\005\022\020\n\010userna" +
                    "me\030\002 \001(\t\022\020\n\010password\030\003 \001(\t\022\021\n\tchannelId\030" +
                    "\004 \001(\t\022\017\n\007success\030\005 \001(\010\022\016\n\006reason\030\006 \001(\t\"l" +
                    "\n\rHpMessageType\022\014\n\010REGISTER\020\000\022\023\n\017REGISTE" +
                    "R_RESULT\020\001\022\r\n\tCONNECTED\020\002\022\020\n\014DISCONNECTE" +
                    "D\020\003\022\010\n\004DATA\020\004\022\r\n\tKEEPALIVE\020\005b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
            .internalBuildGeneratedFileFrom(descriptorData,
                    new com.google.protobuf.Descriptors.FileDescriptor[] {
                    });
    internal_static_HpMessage_descriptor =
            getDescriptor().getMessageTypes().get(0);
    internal_static_HpMessage_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_HpMessage_descriptor,
            new java.lang.String[] { "Type", "MetaData", "Data", });
    internal_static_HpMessage_MetaData_descriptor =
            internal_static_HpMessage_descriptor.getNestedTypes().get(0);
    internal_static_HpMessage_MetaData_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_HpMessage_MetaData_descriptor,
            new java.lang.String[] { "Port", "Username", "Password", "ChannelId", "Success", "Reason", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
