USE [socmedserver]
GO
/****** Object:  Schema [saaccount]    Script Date: 08/08/2015 15:39:57 ******/
CREATE SCHEMA [saaccount] AUTHORIZATION [saaccount]
GO
/****** Object:  Table [dbo].[P_Statuses]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO

GO
CREATE TABLE [dbo].[P_Statuses](
	[status_num] [int] NOT NULL IDENTITY(10000,1),
	[status_name] [varchar](30) NOT NULL,
 CONSTRAINT [PK__P_Status__22C29A8F59C55456] PRIMARY KEY CLUSTERED 
(
	[status_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[P_Statuses] ON
GO
INSERT [dbo].[P_Statuses] ([status_num], [status_name]) VALUES (1000, N'verifying details')
INSERT [dbo].[P_Statuses] ([status_num], [status_name]) VALUES (1001, N'verifying email')
INSERT [dbo].[P_Statuses] ([status_num], [status_name]) VALUES (1002, N'Active')
INSERT [dbo].[P_Statuses] ([status_num], [status_name]) VALUES (1003, N'reject by authentication')
/****** Object:  Table [dbo].[P_RelationTypes]    Script Date: 08/08/2015 15:40:06 ******/
GO
SET IDENTITY_INSERT [dbo].[P_Statuses] OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[P_RelationTypes](
	[relation_type_num] [int] NOT NULL,
	[relation_type_description] [varchar](30) NOT NULL,
 CONSTRAINT [PK__P_Relati__E29F47DE5E8A0973] PRIMARY KEY CLUSTERED 
(
	[relation_type_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[P_Devices]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[P_Devices](
	[device_internal_id] [int] NOT NULL IDENTITY(10000,1),
	[device_external_id] [varchar](30) NULL,
	[device_type] [int] NULL,
	[device_model] [varchar](30) NOT NULL,
	[os] [varchar](30) NOT NULL,
	[os_version] [varchar](30) NOT NULL,
	[cerc_app_version] [varchar](30) NOT NULL,
	[gps_enabled] [int] NOT NULL,
	[nfc_enabled] [int] NOT NULL,
	[sim_enabled] [int] NOT NULL,
	[installation_date] [datetime] NOT NULL,
	[last_update_date] [datetime] NULL,
 CONSTRAINT [PK_P_Devices] PRIMARY KEY CLUSTERED 
(
	[device_internal_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[P_DeviceLog]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[P_DeviceLog](
	[status_history_num] [int] NOT NULL PRIMARY KEY IDENTITY(10000,1),
	[status_num] [int] NOT NULL,
	[community_member_id] [int] NOT NULL,
	[date_from] [datetime] NOT NULL DEFAULT current_timestamp,
	[date_to] [datetime] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[P_CommunityMembers]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO

GO
CREATE TABLE [dbo].[P_CommunityMembers](
	[community_member_id] [int] NOT NULL IDENTITY(10000,1),
	[external_id] [varchar](25) NOT NULL,
	[external_id_type] [int] NOT NULL,
	[first_name] [varchar](50) NOT NULL,
	[last_name] [varchar](50) NOT NULL,
	[birth_date] [date] NOT NULL,
	[gender] [int] NOT NULL,
	[state] [varchar](50) NOT NULL,
	[city] [varchar](50) NOT NULL,
	[street] [varchar](50) NOT NULL,
	[house_number] [int] NULL,
	[zip_code] [varchar](15) NULL,
	[home_phone_number] [varchar](20) NULL,
	[mobile_phone_number] [varchar](20) NOT NULL,
	[email_address] [varchar](50) NOT NULL,
 	[gui_description] [varchar](200) NULL DEFAULT current_timestamp,
 CONSTRAINT [PK__P_Commun__CA3297CC398D8EEE] PRIMARY KEY CLUSTERED 
(
	[community_member_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[P_CommunityMembers] ON
GO
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1002, N'301234567', 0, N'eli', N'gutmann', CAST(0x8BF70A00 AS Date), 0, N'Israel', N'Haifa', N'Herzel', NULL, NULL, NULL, N'0501234567', N'ercserver@gmail.com', CAST(0x0000A48A0096D50C AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1003, N'311234567', 0, N'avi', N'asulin', CAST(0xD3FB0A00 AS Date), 0, N'Israel', N'Ramat-Gan', N'Herzel', NULL, NULL, NULL, N'0521234567', N'erc2server@gmail.com', CAST(0x0000A48A00B5A907 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1005, N'22234342234', 0, N'tiger', N'tigris', CAST(0xDFED0700 AS Date), 1, N'nearTree', N'garbge', N'gulgel', 12, N'1313', N'054-7656666', N'054-767756756', N'shbe77@gmail.com', CAST(0x0000A48E0084BD00 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1011, N'111111111', 0, N'Shlomit', N'Sibony', CAST(0x361B0B00 AS Date), 0, N'israel', N'Nahariyya', N'HaZamir', 8, N'00000', NULL, N'052222222222', N'shlomit1414@hotmail.com', CAST(0x0000A49A00C920E7 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1012, N'111111111', 0, N'Shlomit', N'Sibony', CAST(0x361B0B00 AS Date), 0, N'israel', N'Nahariyya', N'HaZamir', 8, N'00000', NULL, N'052222222222', N'shlomit1414@hotmail.com', CAST(0x0000A49A00C967A7 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1013, N'111111111', 0, N'Naor', N'Sibony', CAST(0x361B0B00 AS Date), 0, N'israel', N'Nahariyya', N'HaZamir', 8, N'00000', NULL, N'052222222222', N'NaorSibony@gmail.com', CAST(0x0000A49A00CB8F7F AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1014, N'111111111', 0, N'Naor', N'Sibony', CAST(0x361B0B00 AS Date), 0, N'israel', N'Nahariyya', N'HaZamir', 8, N'00000', NULL, N'052222222222', N'NaorSibony111@gmail.com', CAST(0x0000A49A00CC54DE AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1015, N'111111111', 0, N'Naor', N'Sibony', CAST(0x361B0B00 AS Date), 0, N'israel', N'Nahariyya', N'HaZamir', 8, N'00000', NULL, N'052222222222', N'NaorSibony1111@gmail.com', CAST(0x0000A49A00CD77C4 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1016, N'111111111', 0, N'Naor', N'Sibony', CAST(0x361B0B00 AS Date), 0, N'israel', N'Nahariyya', N'HaZamir', 8, N'00000', NULL, N'052222222222', N'NaorSibony1111@gmail.com', CAST(0x0000A49B00D43AEB AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1017, N'111111111', 0, N'Naor', N'Sibony', CAST(0x361B0B00 AS Date), 0, N'israel', N'Nahariyya', N'HaZamir', 8, N'00000', NULL, N'052222222222', N'NaorSibony1111@gmail.com', CAST(0x0000A49B00D4DD55 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1018, N'111111111', 0, N'Naor', N'Sibony', CAST(0x361B0B00 AS Date), 0, N'israel', N'Nahariyya', N'HaZamir', 8, N'00000', NULL, N'052222222222', N'NaorSibony1111@gmail.com', CAST(0x0000A49B00D55ECA AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1019, N'111111111', 0, N'Ohad', N'Gur', CAST(0x361B0B00 AS Date), 0, N'israel', N'Nahariyya', N'HaZamir', 8, N'00000', NULL, N'052222222222', N'bla', CAST(0x0000A49B00D59EBB AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1020, N'111111111', 0, N'Ohad', N'Gur', CAST(0x361B0B00 AS Date), 0, N'israel', N'Nahariyya', N'HaZamir', 8, N'00000', NULL, N'052222222222', N'ornagur@gmail.com', CAST(0x0000A49B00D71F53 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1021, N'111111111', 0, N'Ohad', N'Gur', CAST(0x361B0B00 AS Date), 0, N'israel', N'Nahariyya', N'HaZamir', 8, N'00000', NULL, N'052222222222', N'ornagur@gmail.com', CAST(0x0000A49B00D74750 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1022, N'111111111', 0, N'Ohad', N'Gur', CAST(0x361B0B00 AS Date), 0, N'israel', N'Nahariyya', N'HaZamir', 8, N'00000', NULL, N'052222222222', N'bla@gmail.com', CAST(0x0000A49B00D886AB AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1024, N'1234', 0, N'Arbel', N'Axelrod', CAST(0x86960A00 AS Date), 1, N'israel', N'Nahariyya', N'HaZamir', 8, N'00000', NULL, N'052222222222', N'arbelax@gmil.com', CAST(0x0000A4B200846C89 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1025, N'1234', 0, N'Arbel', N'Axelrod', CAST(0x86960A00 AS Date), 1, N'israel', N'Nahariyya', N'HaZamir', 8, N'00000', NULL, N'052222222222', N'''arbelax@gmail.com''', CAST(0x0000A4B2008572BE AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1030, N'1234', 0, N'Arbel', N'Axelrod', CAST(0x86960A00 AS Date), 1, N'israel', N'Nahariyya', N'HaZamir', 8, N'00000', NULL, N'052222222222', N'''arbelax@gail.com''', CAST(0x0000A4B2008D0459 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1031, N'1234', 0, N'Arbel', N'Axelrod', CAST(0x86960A00 AS Date), 1, N'israel', N'Nahariyya', N'HaZamir', 8, N'00000', NULL, N'052222222222', N'''arbelax@gail.com''', CAST(0x0000A4B2008FC905 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1032, N'1234', 0, N'Arbel', N'Axelrod', CAST(0x86960A00 AS Date), 1, N'israel', N'Nahariyya', N'HaZamir', 8, N'00000', NULL, N'052222222222', N'''arbelax@gail.com''', CAST(0x0000A4B200BE91F3 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1034, N'123456', 1, N'Ohad', N'Gur', CAST(0xF8160B00 AS Date), 1, N'Israel', N'Qiryat Ono', N'PO 52', NULL, NULL, NULL, N'054444444', N'bla', CAST(0x0000A4B200D7BA19 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1035, N'123', 1000, N'shira', N'perry', CAST(0xC3150B00 AS Date), 1, N'israel', N'haifa', N'asd', NULL, NULL, NULL, N'054444444', N'bla', CAST(0x0000A4B300177D89 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1036, N'12345', 0, N'ohad1', N'gur', CAST(0xF8160B00 AS Date), 1, N'Israel', N'ka', N'PO 52', NULL, NULL, NULL, N'054499', N'bla@gmail.com', CAST(0x0000A4B30021DF34 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1037, N'12345', 0, N'ohad1', N'gur', CAST(0xF8160B00 AS Date), 1, N'Israel', N'ka', N'PO 52', NULL, NULL, NULL, N'054499', N'bla@gmail.com', CAST(0x0000A4B30021E141 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1055, N'', 0, N'', N'', CAST(0x5B950A00 AS Date), 0, N'', N'', N'', NULL, NULL, NULL, N'', N'', CAST(0x0000A4BA00A8172B AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1056, N'a', 0, N'', N'', CAST(0x5B950A00 AS Date), 0, N'', N'', N'', NULL, NULL, NULL, N'', N'', CAST(0x0000A4BA00A81D90 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1057, N'12345', 0, N'asd', N'dsa', CAST(0xF8160B00 AS Date), 0, N'Israel', N'Tel Aviv', N'Rechov', NULL, NULL, NULL, N'054321', N'bla', CAST(0x0000A4BA00D91C6F AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1058, N'12345', 0, N'asd', N'dsa', CAST(0xF8160B00 AS Date), 0, N'Israel', N'Tel Aviv', N'Rechov', NULL, NULL, NULL, N'054876', N'bla1@gail.com', CAST(0x0000A4BB006E2381 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1059, N'98765', 0, N'asd', N'dsa', CAST(0xF8160B00 AS Date), 0, N'hjkll', N'lkjhh', N'ghjk', NULL, NULL, NULL, N'098765', N'bla2@gail.com', CAST(0x0000A4BB0071008E AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1060, N'111111', 0, N'asd', N'dsa', CAST(0x38250B00 AS Date), 0, N'Israel', N'bla city', N'bla st', NULL, NULL, NULL, N'07665', N'11111@gail.com', CAST(0x0000A4BB007499D9 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1061, N'985', 0, N'asd', N'dsa', CAST(0x7E070B00 AS Date), 0, N'dasdsa', N'dsadsa', N'asdadsd', NULL, NULL, NULL, N'08765444', N'1122@gail.com', CAST(0x0000A4BB007833A8 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1062, N'985', 0, N'asd', N'dsa', CAST(0x7E070B00 AS Date), 0, N'dasdsa', N'dsadsa', N'asdadsd', NULL, NULL, NULL, N'08765444', N'1122@gail.com', CAST(0x0000A4BB00783C7B AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1063, N'34567', 0, N'asd', N'dsa', CAST(0x7E070B00 AS Date), 0, N'asdad', N'dsadsa', N'sdads', NULL, NULL, NULL, N'0988766', N'987@mail.mail', CAST(0x0000A4BB007CB63D AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1064, N'09876', 0, N'as', N'dsa', CAST(0x7E070B00 AS Date), 0, N'israel', N'dsa', N'asd', NULL, NULL, NULL, N'09876', N'09877@gail.com', CAST(0x0000A4BB0087B289 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1065, N'5678', 0, N'asd', N'asd', CAST(0xC3150B00 AS Date), 0, N'iasra', N'dsa', N'asd', NULL, NULL, NULL, N'08765', N'9876@gail.com', CAST(0x0000A4BB008BA539 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1066, N'87654', 0, N'asd', N'dsa', CAST(0xC3150B00 AS Date), 0, N'hsdfh', N'jdhfsd', N'jsdjdf', NULL, NULL, NULL, N'09876', N'987612@gail.com', CAST(0x0000A4BB008F849D AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1067, N'67890', 0, N'asd', N'dsa', CAST(0xC3150B00 AS Date), 0, N'ksdkld', N'jsdksd', N'kdjd', NULL, NULL, NULL, N'098765', N'ohad@gail.com', CAST(0x0000A4BB00958808 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1068, N'12345', 0, N'asd', N'dsa', CAST(0xC3150B00 AS Date), 0, N'Israel', N'asdasd', N'zxczxc', NULL, NULL, NULL, N'09876', N'mail@mail.com', CAST(0x0000A4BB00A34C10 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1069, N'123456', 0, N'asd', N'dsa', CAST(0xC3150B00 AS Date), 0, N'Israel', N'asd', N'sdfsf', NULL, NULL, NULL, N'0876', N'bla', CAST(0x0000A4BB00B13228 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1070, N'45678', 0, N'asd', N'dsa', CAST(0xF8160B00 AS Date), 0, N'Israel', N'asd', N'sdf', NULL, NULL, NULL, N'098876', N'bla', CAST(0x0000A4BB00B92BC5 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1071, N'45678', 0, N'asd', N'dsa', CAST(0xF8160B00 AS Date), 0, N'Israel', N'asd', N'sdf', NULL, NULL, NULL, N'098876', N'bla', CAST(0x0000A4BB00B92CEC AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1072, N'12345', 0, N'asd', N'dsa', CAST(0xF8160B00 AS Date), 0, N'Israel', N'Qiryat Ono', N'jsdkjfd', NULL, NULL, NULL, N'908897', N'bla', CAST(0x0000A4BB00BC32B8 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1073, N'12234', 0, N'asd', N'dsa', CAST(0xF8160B00 AS Date), 0, N'Israel', N'lkdgf', N';lsdkf', NULL, NULL, NULL, N'12314', N'bla', CAST(0x0000A4BB00BE7BAE AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1074, N'12234', 0, N'asd', N'dsa', CAST(0xF8160B00 AS Date), 0, N'Israel', N'lkdgf', N';lsdkf', NULL, NULL, NULL, N'12314', N'bla', CAST(0x0000A4BB00BE7BD9 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1075, N'1234', 0, N'asd', N'dsa', CAST(0x5B950A00 AS Date), 0, N'Israel', N'??????', N'???????', NULL, NULL, NULL, N'79676', N'bla', CAST(0x0000A4BB00C4C692 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1076, N'12345', 0, N'asd', N'dsa', CAST(0xF8160B00 AS Date), 0, N'Israel', N'sdf', N'sdf', NULL, NULL, NULL, N'345345', N'bla', CAST(0x0000A4BB00D690E3 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1077, N'12345', 0, N'asd', N'dsa', CAST(0xF8160B00 AS Date), 0, N'Israel', N'jihi', N'sdfe', NULL, NULL, NULL, N'24524', N'bla', CAST(0x0000A4BB00D839EB AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1078, N'123456', 0, N'ohad', N'gur', CAST(0xF8160B00 AS Date), 0, N'Israel', N'blabla', N'bla', NULL, NULL, NULL, N'098765', N'bla', CAST(0x0000A4BE0070D352 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1079, N'123456', 0, N'ohad', N'gur', CAST(0xF8160B00 AS Date), 0, N'Israel', N'Qiryat Ono', N'asdasd', NULL, NULL, NULL, N'7876876', N'bla', CAST(0x0000A4BE0077F011 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1080, N'123455', 0, N'ohad', N'gur', CAST(0xF8160B00 AS Date), 0, N'Israel', N'Qiryat Ono', N'asdads', NULL, NULL, NULL, N'87565', N'bla', CAST(0x0000A4BE007B5CFE AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1081, N'123456', 0, N'Ohad', N'Gur', CAST(0xF8160B00 AS Date), 0, N'Israel', N'Qiryat Ono', N'asdads', NULL, NULL, NULL, N'9870786', N'bla', CAST(0x0000A4BE00826890 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1082, N'123456', 0, N'ohad', N'gur', CAST(0xF8160B00 AS Date), 0, N'Israel', N'Qiryat Ono', N'sdfsdf', NULL, NULL, NULL, N'45646', N'bla', CAST(0x0000A4BE008DA35B AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1083, N'123456', 0, N'Ohad', N'Gur', CAST(0xF8160B00 AS Date), 0, N'Israel', N'fdsfsd', N'dfsdf', NULL, NULL, NULL, N'45646', N'ohadgur@gmail.com', CAST(0x0000A4BF004B4DCF AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1084, N'1234', 0, N'Ohad', N'Gur', CAST(0xF8160B00 AS Date), 0, N'Israel', N'iuhiuh', N'yuguytguy', NULL, NULL, NULL, N'876786', N'bla', CAST(0x0000A4BF009507C5 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1085, N'456789', 0, N'asd', N'dsa', CAST(0x68180B00 AS Date), 0, N'Israel', N'oig', N'jhg', NULL, NULL, NULL, N'987', N'bla', CAST(0x0000A4BF0099F685 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1086, N'1245', 0, N'asd', N'dsa', CAST(0x60E00600 AS Date), 0, N'Israel', N'uyguyg', N'ghjg', NULL, NULL, NULL, N'876', N'bla', CAST(0x0000A4BF009CC4B7 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1087, N'1234', 0, N'asd', N'dsa', CAST(0x46A60A00 AS Date), 0, N'Israel', N'sdf', N'asd', NULL, NULL, NULL, N'875', N'bla', CAST(0x0000A4BF00A26BDA AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1088, N'1234', 0, N'asd', N'dsa', CAST(0xF3340B00 AS Date), 0, N'Israel', N'ujghiug', N'hghg', NULL, NULL, NULL, N'7687576', N'bla', CAST(0x0000A4BF00A7160C AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1089, N'12345', 0, N'Ohad', N'Gur', CAST(0xF3340B00 AS Date), 0, N'israel', N'sdfsdf', N'asdadf', NULL, NULL, NULL, N'787', N'bla', CAST(0x0000A4C0005FC064 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1091, N'1234', 0, N'ohad', N'gur', CAST(0x82920500 AS Date), 0, N'Israel', N'asdasd', N'asdasd', NULL, NULL, NULL, N'65654', N'bla', CAST(0x0000A4C000647A92 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1092, N'1234', 0, N'ohad', N'gur', CAST(0x38A20500 AS Date), 0, N'israel', N'asdasd', N'asddsf', NULL, NULL, NULL, N'8776', N'bla', CAST(0x0000A4C00065D2B8 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1093, N'12345', 0, N'Arbel', N'Axelrod', CAST(0x75250B00 AS Date), 0, N'aaa', N'aaa', N'aaa', NULL, NULL, NULL, N'111111', N'bla', CAST(0x0000A4C0008A0E38 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1094, N'12345', 0, N'Arbel', N'Axelrod', CAST(0x75250B00 AS Date), 1, N'Israel', N'aa', N'aa', NULL, NULL, NULL, N'1111', N'bla', CAST(0x0000A4C000928C1A AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1095, N'12345', 0, N'Arbel', N'Axelrod', CAST(0x75250B00 AS Date), 1, N'Israel', N'aa', N'aa', NULL, NULL, NULL, N'111', N'bla', CAST(0x0000A4C000ACCC00 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1096, N'98765', 0, N'asd', N'gur', CAST(0xF8160B00 AS Date), 0, N'Israel', N'dufhsd', N'hjgjhg', NULL, NULL, NULL, N'876876', N'bla', CAST(0x0000A4C100BA7774 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1097, N'7654', 0, N'Ohad', N'Gur', CAST(0xFB160B00 AS Date), 0, N'Israel', N'ytfytf', N'ytfytf', NULL, NULL, NULL, N'78658765', N'bla', CAST(0x0000A4C100D65815 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1098, N'12345', 0, N'new', N'doctor', CAST(0xC3150B00 AS Date), 0, N'Israel', N'dfghjk', N'dfghjkl', NULL, NULL, NULL, N'76876', N'bla', CAST(0x0000A4C100F6DDE8 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1099, N'123455', 0, N'new', N'doctor', CAST(0xF3340B00 AS Date), 0, N'Israel', N'kljlj', N'lsf', NULL, NULL, NULL, N'876876', N'bla', CAST(0x0000A4C100F90237 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1101, N'876975', 0, N'asd', N'asdasd', CAST(0xC3150B00 AS Date), 0, N'Israel', N'ighiuhui', N'hiuyi', NULL, NULL, NULL, N'987979', N'bla', CAST(0x0000A4C100FC039B AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1102, N'5865', 0, N'asd', N'asd', CAST(0xC3150B00 AS Date), 0, N'Israel', N'yuguyg', N'uyguyg', NULL, NULL, NULL, N'786786', N'bla', CAST(0x0000A4C200404779 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1103, N'675765', 0, N'asd', N'dsa', CAST(0xC3150B00 AS Date), 0, N'Israel', N'ygy', N'hguhg', NULL, NULL, NULL, N'786876', N'ohadgur@bla.com', CAST(0x0000A4C20041AD83 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1104, N'1234', 0, N'm', N'g', CAST(0x1EC10600 AS Date), 1, N'db', N'agag', N'sgsgs', NULL, NULL, NULL, N'152515162', N'bla', CAST(0x0000A4C200A8BD39 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1105, N'1234', 0, N'm', N'g', CAST(0x1EC10600 AS Date), 1, N'sh', N'gw', N'sg', NULL, NULL, NULL, N'15151', N'bla', CAST(0x0000A4C200AC675D AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1119, N'1234', 0, N'm', N'g', CAST(0x59130B00 AS Date), 1, N'ds', N'dhshs', N'dgdhdh', NULL, NULL, NULL, N'262525251', N'bla', CAST(0x0000A4C30042BE8C AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1120, N'1234', 0, N'm', N'g', CAST(0x59130B00 AS Date), 1, N'ds', N'dhshs', N'dgdhdh', NULL, NULL, NULL, N'262525251', N'bla', CAST(0x0000A4C300433CFB AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1122, N'1234', 0, N'm', N'g', CAST(0x59130B00 AS Date), 1, N'ds', N'dhshs', N'dgdhdh', NULL, NULL, NULL, N'262525251', N'bla', CAST(0x0000A4C300436EE6 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1123, N'1234', 0, N'm', N'g', CAST(0x59130B00 AS Date), 1, N'ds', N'dhshs', N'dgdhdh', NULL, NULL, NULL, N'262525251', N'bla', CAST(0x0000A4C30043CFB5 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1124, N'1234', 0, N'm', N'g', CAST(0x59130B00 AS Date), 1, N'ds', N'dhshs', N'dgdhdh', NULL, NULL, NULL, N'262525251', N'bla', CAST(0x0000A4C300470381 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1125, N'1234', 0, N'm', N'g', CAST(0x59130B00 AS Date), 1, N'ds', N'dhshs', N'dgdhdh', NULL, NULL, NULL, N'262525251', N'bla', CAST(0x0000A4C30047CD45 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1127, N'9876', 0, N'avia', N'ber', CAST(0xC3150B00 AS Date), 1, N'israel', N'blaa', N'blaa', NULL, NULL, NULL, N'0543333333', N'bavias54@gmail.com', CAST(0x0000A4C30066535E AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1128, N'8765', 0, N'avia', N'ber', CAST(0xC3150B00 AS Date), 1, N'israel', N'bla', N'bla', NULL, NULL, NULL, N'0544444444', N'bavias54@gmail.com', CAST(0x0000A4C5002EB728 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1129, N'1234', 0, N'm', N'g', CAST(0x59130B00 AS Date), 1, N'ds', N'dhshs', N'dgdhdh', NULL, NULL, NULL, N'262525251', N'bla', CAST(0x0000A4C500541E54 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1130, N'1234', 0, N'm', N'g', CAST(0x59130B00 AS Date), 1, N'ds', N'dhshs', N'dgdhdh', NULL, NULL, NULL, N'262525251', N'bla', CAST(0x0000A4C500555712 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1131, N'1234', 0, N'm', N'g', CAST(0x59130B00 AS Date), 1, N'ds', N'dhshs', N'dgdhdh', NULL, NULL, NULL, N'262525251', N'bla', CAST(0x0000A4C50059251A AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1132, N'1234', 0, N'm', N'g', CAST(0x59130B00 AS Date), 1, N'ds', N'dhshs', N'dgdhdh', NULL, NULL, NULL, N'262525251', N'bla', CAST(0x0000A4C50059EA57 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1133, N'1234', 0, N'm', N'g', CAST(0x59130B00 AS Date), 1, N'Israel', N'dhshs', N'dgdhdh', NULL, NULL, NULL, N'262525251', N'bla', CAST(0x0000A4C500616F8D AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1135, N'4567', 0, N'hen', N'berdugo', CAST(0x0A190B00 AS Date), 1, N'israel', N'gsg', N'fsd', NULL, NULL, NULL, N'054-7777777', N'hen_berdugo@walla.com', CAST(0x0000A4C60096D618 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1136, N'4567', 0, N'hen', N'berdugo', CAST(0x0A190B00 AS Date), 1, N'israel', N'gsg', N'fsd', NULL, NULL, NULL, N'054-7777777', N'hen_berdugo@walla.com', CAST(0x0000A4C60096D61D AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1137, N'4567', 0, N'hen', N'berdugo', CAST(0x0A190B00 AS Date), 1, N'israel', N'gsg', N'fsd', NULL, NULL, NULL, N'054-7777777', N'hen_berdugo@walla.com', CAST(0x0000A4C60096D626 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1138, N'1234', 0, N'm', N'g', CAST(0x59130B00 AS Date), 1, N'Israel', N'dhshs', N'dgdhdh', NULL, NULL, NULL, N'262525251', N'bla', CAST(0x0000A4C600A125DA AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1139, N'1234', 1, N'm', N'g', CAST(0x59130B00 AS Date), 1, N'Israel', N'hcf', N'gv', NULL, NULL, NULL, N'432564345', N'dalsadancingonline@gmail.com', CAST(0x0000A4C600A18CA6 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1140, N'1111', 0, N'arbel', N'axelrod', CAST(0x9D180B00 AS Date), 1, N'Israel', N'aaa', N'aa', NULL, NULL, NULL, N'05444444', N'arbelax@gmail.com', CAST(0x0000A4C600A197BA AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1141, N'1234', 1, N'meni', N'grossman', CAST(0xAA2F0600 AS Date), 0, N'Israel', N'sham', N'sham2', NULL, NULL, NULL, N'123456789', N'bla', CAST(0x0000A4C600BB7F91 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1142, N'1234', 1, N'meni', N'grossman', CAST(0xAA2F0600 AS Date), 0, N'Israel', N'sham', N'sham2', NULL, NULL, NULL, N'123456789', N'bla', CAST(0x0000A4C600BB9A60 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1143, N'1234', 1, N'meni', N'grossman', CAST(0x1EC10600 AS Date), 0, N'Israel', N'str', N'isis', NULL, NULL, NULL, N'12436372', N'bla', CAST(0x0000A4C600BCA265 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1144, N'1', 0, N'a', N'b', CAST(0xAA2F0600 AS Date), 0, N'aa', N'aa', N'bb', NULL, NULL, NULL, N'166161', N'bla', CAST(0x0000A4C600BE964F AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1145, N'12', 0, N'a', N'b', CAST(0xAA2F0600 AS Date), 0, N'a', N'a', N'b', NULL, NULL, NULL, N'1', N'bla', CAST(0x0000A4C600C05A28 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1146, N'12345', 0, N'asd', N'asdasd', CAST(0xC3150B00 AS Date), 0, N'Israel', N'asasd', N'sdfsd', NULL, NULL, NULL, N'453534', N'maorsegal6@bla.com', CAST(0x0000A4C600C48072 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1147, N'1234', 0, N'asd', N'dsa', CAST(0xC3150B00 AS Date), 0, N'Israel', N'asda', N'sdfsd', NULL, NULL, NULL, N'845489', N'maorsegal6@walla.com', CAST(0x0000A4C600C6B83C AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1148, N'1234', 0, N'meni', N'grossman', CAST(0xAA2F0600 AS Date), 0, N'Israel', N'a', N'b', NULL, NULL, NULL, N'2020', N'bla', CAST(0x0000A4C600CBA10E AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1149, N'1234', 0, N'mm', N'gg', CAST(0xB42F0600 AS Date), 0, N'Israel', N'a', N'b', NULL, NULL, NULL, N'22', N'bla', CAST(0x0000A4C600CD684F AS DateTime), NULL)
GO
print 'Processed 100 total records'
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1150, N'1234', 0, N'meni', N'g', CAST(0xAA2F0600 AS Date), 0, N'Israel', N'a', N'a', NULL, NULL, NULL, N'123445', N'bla', CAST(0x0000A4C600CFB955 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1151, N'1234', 0, N'meni', N'g', CAST(0xAA2F0600 AS Date), 0, N'Israel', N'a', N'a', NULL, NULL, NULL, N'16262', N'bla', CAST(0x0000A4C600D18E7C AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1152, N'1234', 0, N'meni', N'g', CAST(0xAA2F0600 AS Date), 0, N'Israel', N'a', N'a', NULL, NULL, NULL, N'1237373', N'bla', CAST(0x0000A4C600D4FDD0 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1153, N'1234', 0, N'meni', N'man', CAST(0xAA2F0600 AS Date), 0, N'Israel', N'a', N'a', NULL, NULL, NULL, N'15252', N'bla', CAST(0x0000A4C600E04FC9 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1154, N'1', 0, N'meni', N'grossman', CAST(0xAA2F0600 AS Date), 0, N'Israel', N'a', N'a', NULL, NULL, NULL, N'1000', N'bla', CAST(0x0000A4C700BB5180 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1157, N'1234', 0, N'asd', N'asd', CAST(0xC3150B00 AS Date), 0, N'Israel', N'sdfsd', N'sdfd', NULL, NULL, NULL, N'67543', N'dsfj@df', CAST(0x0000A4C700C323B8 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1158, N'100000', 0, N'm', N'gr', CAST(0xA6AE0600 AS Date), 0, N'Israel', N'a', N'a', NULL, NULL, NULL, N'10000', N'bla', CAST(0x0000A4C700C4B75B AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1159, N'10000', 0, N'm', N'gr', CAST(0x9A220B00 AS Date), 0, N'Israel', N'a', N'a', NULL, NULL, NULL, N'19999', N'bla', CAST(0x0000A4C700C71A6F AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1160, N'100', 0, N'm', N'gr', CAST(0xA4040B00 AS Date), 0, N'Israel', N'a', N'b', NULL, NULL, NULL, N'1238383', N'bla', CAST(0x0000A4C700C9801D AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1161, N'1234', 0, N'asd', N'dsa', CAST(0xC3150B00 AS Date), 0, N'Israel', N'asdsd', N'ssdf', NULL, NULL, NULL, N'564654', N'dfggfd@dfd', CAST(0x0000A4C700DCA166 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1162, N'45453', 0, N'', N'', CAST(0x5B950A00 AS Date), 0, N'', N'', N'', NULL, NULL, NULL, N'', N'', CAST(0x0000A4C700DDEEB4 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1164, N'10', 0, N'm', N'g', CAST(0x7E070B00 AS Date), 0, N'Israel', N'a', N'a', NULL, NULL, NULL, N'1000', N'bla', CAST(0x0000A4C8007E8319 AS DateTime), NULL)
INSERT [dbo].[P_CommunityMembers] ([community_member_id], [external_id], [external_id_type], [first_name], [last_name], [birth_date], [gender], [state], [city], [street], [house_number], [zip_code], [home_phone_number], [mobile_phone_number], [email_address], [member_since], [gui_description]) VALUES (1165, N'10', 0, N'm', N'g', CAST(0xE8120B00 AS Date), 0, N'israel', N'a', N'a', NULL, NULL, NULL, N'1000', N'bla', CAST(0x0000A4C80080C119 AS DateTime), NULL)
/****** Object:  Table [dbo].[O_EventStatuses]    Script Date: 08/08/2015 15:40:06 ******/
GO
SET IDENTITY_INSERT [dbo].[P_CommunityMembers] OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[O_EventStatuses](
	[status_num] [int] NOT NULL IDENTITY(10000,1),
	[status_name] [varchar](30) NOT NULL,
 CONSTRAINT [PK_O_EventStatuses] PRIMARY KEY CLUSTERED 
(
	[status_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[O_EventStatuses] ON
GO
INSERT [dbo].[O_EventStatuses] ([status_num], [status_name]) VALUES (1000, N'active')
INSERT [dbo].[O_EventStatuses] ([status_num], [status_name]) VALUES (1001, N'canceled')
INSERT [dbo].[O_EventStatuses] ([status_num], [status_name]) VALUES (1002, N'finished')
/****** Object:  Table [dbo].[O_AutomaticDispensers]    Script Date: 08/08/2015 15:40:06 ******/
GO
SET IDENTITY_INSERT [dbo].[O_EventStatuses] OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[O_AutomaticDispensers](
	[dispensers_num] [int] NOT NULL IDENTITY(10000,1),
	[dispensers_name] [varchar](30) NOT NULL,
	[dispensers_location] [varchar](100) NOT NULL,
 CONSTRAINT [PK_O_AutomaticDispensers] PRIMARY KEY CLUSTERED 
(
	[dispensers_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[O_ActionTypes]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[O_ActionTypes](
	[action_type_num] [int] NOT NULL IDENTITY(10000,1),
	[action_type_name] [varchar](100) NOT NULL,
 CONSTRAINT [PK__O_Action__B023E6822EDAF651] PRIMARY KEY CLUSTERED 
(
	[action_type_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[O_ActionStatus]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[O_ActionStatus](
	[status_name] [varchar](30) NOT NULL,
	[action_status_num] [int] IDENTITY(1000,1) NOT NULL,
 CONSTRAINT [PK__O_Action__B023E68232E0915F] PRIMARY KEY CLUSTERED 
(
	[action_status_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[O_ActionStatus] ON
INSERT [dbo].[O_ActionStatus] ([status_name], [action_status_num]) VALUES (N'open', 1000)
SET IDENTITY_INSERT [dbo].[O_ActionStatus] OFF
/****** Object:  Table [dbo].[MP_Specialization]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[MP_Specialization](
	[specialization_id] [int] NOT NULL IDENTITY(10000,1),
	[specialization_description] [varchar](3000) NULL,
 CONSTRAINT [PK__MP_Speci__0E5BB650395884C4] PRIMARY KEY CLUSTERED 
(
	[specialization_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[MP_Specialization] ON
GO
INSERT [dbo].[MP_Specialization] ([specialization_id], [specialization_description]) VALUES (1000, N'Oncology')
INSERT [dbo].[MP_Specialization] ([specialization_id], [specialization_description]) VALUES (1001, N'Allergy and Immunology
')
INSERT [dbo].[MP_Specialization] ([specialization_id], [specialization_description]) VALUES (1002, N'Anesthesia')
INSERT [dbo].[MP_Specialization] ([specialization_id], [specialization_description]) VALUES (1004, N'Dermatology')
INSERT [dbo].[MP_Specialization] ([specialization_id], [specialization_description]) VALUES (1005, N'Emergency Medicine')
INSERT [dbo].[MP_Specialization] ([specialization_id], [specialization_description]) VALUES (1006, N'Endocrinology and Metabolism')
INSERT [dbo].[MP_Specialization] ([specialization_id], [specialization_description]) VALUES (1007, N'Family Practice')
INSERT [dbo].[MP_Specialization] ([specialization_id], [specialization_description]) VALUES (1008, N'Gastroenterology')
INSERT [dbo].[MP_Specialization] ([specialization_id], [specialization_description]) VALUES (1009, N'General Practice')
INSERT [dbo].[MP_Specialization] ([specialization_id], [specialization_description]) VALUES (1010, N'Geriatric Medicine')
INSERT [dbo].[MP_Specialization] ([specialization_id], [specialization_description]) VALUES (1011, N'Gynecology')
INSERT [dbo].[MP_Specialization] ([specialization_id], [specialization_description]) VALUES (1012, N'Gynecologic Oncology')
INSERT [dbo].[MP_Specialization] ([specialization_id], [specialization_description]) VALUES (1013, N'Hematology')
INSERT [dbo].[MP_Specialization] ([specialization_id], [specialization_description]) VALUES (1014, N'Infectious Diseases')
INSERT [dbo].[MP_Specialization] ([specialization_id], [specialization_description]) VALUES (1015, N'Internal Medicine')
INSERT [dbo].[MP_Specialization] ([specialization_id], [specialization_description]) VALUES (1016, N'Neonatology')
INSERT [dbo].[MP_Specialization] ([specialization_id], [specialization_description]) VALUES (1017, N'Nephrology')
INSERT [dbo].[MP_Specialization] ([specialization_id], [specialization_description]) VALUES (1018, N'Neurology')
INSERT [dbo].[MP_Specialization] ([specialization_id], [specialization_description]) VALUES (1019, N'Neurological Surgery')
INSERT [dbo].[MP_Specialization] ([specialization_id], [specialization_description]) VALUES (1020, N'Obstetrics and Gynecology')
/****** Object:  Table [dbo].[MP_Positions]    Script Date: 08/08/2015 15:40:06 ******/
SET IDENTITY_INSERT [dbo].[MP_Specialization] OFF
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[MP_Positions](
	[position_num] [int] NOT NULL IDENTITY(10000,1),
	[position_description] [varchar](50) NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[MP_Positions] ON
GO
INSERT [dbo].[MP_Positions] ([position_num], [position_description]) VALUES (1000, N'doctor')
GO
SET IDENTITY_INSERT [dbo].[MP_Positions] OFF
GO
/****** Object:  Table [dbo].[MP_OrganizationTypes]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[MP_OrganizationTypes](
	[organization_type_num] [int] NOT NULL IDENTITY(10000,1),
	[organization_type_description] [varchar](30) NOT NULL,
 CONSTRAINT [PK_MP_OrganizationTypes] PRIMARY KEY CLUSTERED 
(
	[organization_type_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[MP_OrganizationTypes] ON
GO
INSERT [dbo].[MP_OrganizationTypes] ([organization_type_num], [organization_type_description]) VALUES (1000, N'kupat-holim')
/****** Object:  Table [dbo].[Enum]    Script Date: 08/08/2015 15:40:06 ******/
GO
SET IDENTITY_INSERT [dbo].[MP_OrganizationTypes] OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Enum](
	[table_name] [varchar](30) NOT NULL,
	[column_name] [varchar](30) NOT NULL,
	[enum_code] [int] NOT NULL,
	[enum_value] [varchar](30) NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[Enum] ([table_name], [column_name], [enum_code], [enum_value]) VALUES (N'P_CommunityMembers', N'gender', 0, N'male')
INSERT [dbo].[Enum] ([table_name], [column_name], [enum_code], [enum_value]) VALUES (N'P_CommunityMembers', N'gender', 1, N'female')
INSERT [dbo].[Enum] ([table_name], [column_name], [enum_code], [enum_value]) VALUES (N'DefaultCallerSettings', N'default_caller', 0, N'you')
INSERT [dbo].[Enum] ([table_name], [column_name], [enum_code], [enum_value]) VALUES (N'DefaultCallerSettings', N'default_caller', 1, N'server')
INSERT [dbo].[Enum] ([table_name], [column_name], [enum_code], [enum_value]) VALUES (N'P_CommunityMembers', N'external_id_type', 0, N'IdCard')
INSERT [dbo].[Enum] ([table_name], [column_name], [enum_code], [enum_value]) VALUES (N'P_CommunityMembers', N'external_id_type', 1, N'Passport')
INSERT [dbo].[Enum] ([table_name], [column_name], [enum_code], [enum_value]) VALUES (N'registrationFields', N'fields_group', 0, N'personal')
INSERT [dbo].[Enum] ([table_name], [column_name], [enum_code], [enum_value]) VALUES (N'registrationFields', N'fields_group', 1, N'medical')
INSERT [dbo].[Enum] ([table_name], [column_name], [enum_code], [enum_value]) VALUES (N'registrationFields', N'fields_group', 2, N'preferences ')
INSERT [dbo].[Enum] ([table_name], [column_name], [enum_code], [enum_value]) VALUES (N'registrationFields', N'fields_group', 3, N'professional ')
INSERT [dbo].[Enum] ([table_name], [column_name], [enum_code], [enum_value]) VALUES (N'registrationFields', N'type', 0, N'string')
INSERT [dbo].[Enum] ([table_name], [column_name], [enum_code], [enum_value]) VALUES (N'registrationFields', N'type', 1, N'int')
INSERT [dbo].[Enum] ([table_name], [column_name], [enum_code], [enum_value]) VALUES (N'registrationFields', N'type', 2, N'float')
INSERT [dbo].[Enum] ([table_name], [column_name], [enum_code], [enum_value]) VALUES (N'registrationFields', N'type', 3, N'datetime')
INSERT [dbo].[Enum] ([table_name], [column_name], [enum_code], [enum_value]) VALUES (N'registrationFields', N'type', 4, N'timestamp')
/****** Object:  Table [dbo].[DoctorAuthorizers]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[DoctorAuthorizers](
	[state] [varchar](50) NOT NULL,
	[email_address] [varchar](100) NOT NULL,
 CONSTRAINT [PK_DoctorAuthorizers] PRIMARY KEY CLUSTERED 
(
	[state] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[DoctorAuthorizers] ([state], [email_address]) VALUES (N'Israel', N'ohadgur@gmail.com')
/****** Object:  Table [dbo].[DefaultCallerSettings]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[DefaultCallerSettings](
	[state] [varchar](50) NOT NULL,
	[default_caller] [int] NOT NULL,
 CONSTRAINT [PK_DefaultCallerSettings] PRIMARY KEY CLUSTERED 
(
	[state] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[DefaultCallerSettings] ([state], [default_caller]) VALUES (N'Israel', 0)
/****** Object:  Table [dbo].[DefaultCallers]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[DefaultCallers](
	[State] [varchar](50) NOT NULL,
	[DefaultCaller] [int] NOT NULL,
 CONSTRAINT [PK_DefaultCallers] PRIMARY KEY CLUSTERED 
(
	[State] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ConfirmationWays]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ConfirmationWays](
	[State] [varchar](50) NOT NULL,
	[ConfirmationWay] [int] NOT NULL,
 CONSTRAINT [PK_ConfirmationWays] PRIMARY KEY CLUSTERED 
(
	[State] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[M_BrandNames]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[M_BrandNames](
	[brand_name_id] [int] NOT NULL IDENTITY(10000,1),
	[brand_name_external_id] [varchar](30) NULL,
	[brand_name_description] [varchar](30) NULL,
	[manufacturer] [varchar](30) NULL,
 CONSTRAINT [PK_M_BrandNames] PRIMARY KEY CLUSTERED 
(
	[brand_name_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[M_ActiveComponents]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[M_ActiveComponents](
	[active_component_id] [int] NOT NULL IDENTITY(10000,1),
	[active_component_description] [varchar](30) NOT NULL,
 CONSTRAINT [PK_M_ActiveComponents] PRIMARY KEY CLUSTERED 
(
	[active_component_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[P_Medications]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[P_Medications](
	[medication_num] [int] NOT NULL IDENTITY(10000,1),
	[medication_name] [varchar](100) NOT NULL,
 CONSTRAINT [PK_P_Medications] PRIMARY KEY CLUSTERED 
(
	[medication_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[P_Medications] ON
GO
INSERT [dbo].[P_Medications] ([medication_num], [medication_name]) VALUES (1000, N'akamol4')
/****** Object:  Table [dbo].[P_EmergencyContact]    Script Date: 08/08/2015 15:40:06 ******/
GO
SET IDENTITY_INSERT [dbo].[P_Medications] OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[P_EmergencyContact](
	[community_member_id] [int] NOT NULL ,
	[contact_phone] [varchar](20) NOT NULL,
 CONSTRAINT [PK__P_Emerge__CA3297CC17036CC0] PRIMARY KEY CLUSTERED 
(
	[community_member_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1003, N'0535768904')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1012, N'0521123456')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1013, N'0521123456')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1016, N'0521123456')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1017, N'0521123456')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1018, N'0521123456')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1019, N'0521123456')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1020, N'0521123456')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1021, N'0521123456')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1022, N'0521123456')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1025, N'0521123456')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1031, N'0521123456')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1032, N'0521123456')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1055, N'')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1056, N'')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1057, N'054321')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1058, N'054321')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1059, N'98876')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1060, N'098765')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1061, N'098744')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1062, N'098744')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1063, N'09876')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1064, N'09876')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1065, N'09876')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1066, N'09876')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1067, N'87765')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1068, N'98765')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1069, N'09876')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1070, N'077655')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1071, N'077655')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1072, N'9766')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1073, N'123134')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1074, N'123134')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1075, N'98776')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1076, N'45345')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1077, N'324')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1078, N'098765')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1079, N'08796875')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1080, N'9676')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1081, N'786876')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1082, N'345345')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1083, N'456456')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1084, N'43534')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1085, N'876')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1086, N'543')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1087, N'875')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1088, N'86654')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1089, N'764')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1091, N'3454654')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1092, N'8787')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1093, N'111111')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1094, N'1111')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1095, N'111')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1096, N'45345')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1097, N'78565')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1098, N'675675')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1099, N'876876')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1101, N'876876')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1102, N'876876')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1103, N'76876')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1104, N'26261441')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1105, N'1625251')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1119, N'272722622')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1120, N'272722622')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1122, N'272722622')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1123, N'272722622')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1124, N'272722622')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1125, N'272722622')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1127, N'0542222222')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1128, N'0543333333')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1129, N'272722622')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1130, N'272722622')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1131, N'272722622')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1132, N'272722622')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1133, N'272722622')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1135, N'054-6666666')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1136, N'054-6666666')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1137, N'054-6666666')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1138, N'272722622')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1139, N'142453563')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1140, N'44444')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1141, N'123456789')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1142, N'123456789')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1143, N'123456788')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1144, N'1772')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1145, N'12621')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1146, N'54345')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1147, N'234234')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1148, N'2020')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1149, N'11')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1150, N'1234')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1151, N'2041')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1152, N'2054')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1153, N'2135')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1154, N'1919')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1157, N'76543')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1158, N'1955')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1159, N'2003')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1160, N'2012')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1161, N'6456456')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1162, N'')
GO
print 'Processed 100 total records'
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1164, N'1538')
INSERT [dbo].[P_EmergencyContact] ([community_member_id], [contact_phone]) VALUES (1165, N'1547')
/****** Object:  Table [dbo].[AuthenticationMethod]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[AuthenticationMethod](
	[state] [varchar](50) NOT NULL,
	[method] [int] NOT NULL,
 CONSTRAINT [PK_AuthenticationMethod] PRIMARY KEY CLUSTERED 
(
	[state] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[AuthenticationMethod] ([state], [method]) VALUES (N'Israel', 0)
/****** Object:  Table [dbo].[MembersLoginDetails]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MembersLoginDetails](
	[community_member_id] [int] NOT NULL,
	[password] [varchar](30) NOT NULL,
	[email_address] [varchar](30) NOT NULL,
 CONSTRAINT [PK_MembersLoginDetails] PRIMARY KEY CLUSTERED 
(
	[community_member_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1002, N'1234', N'ercserver@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1003, N'123', N'erc2server@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1012, N'1111111', N'shlomit1414@hotmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1013, N'1111111', N'NaorSibony@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1014, N'1111111', N'NaorSibony111@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1015, N'1111111', N'NaorSibony1111@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1016, N'1111111', N'NaorSibony1111@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1017, N'1111111', N'NaorSibony1111@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1018, N'1111111', N'NaorSibony1111@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1019, N'1111111', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1020, N'1111111', N'ornagur@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1021, N'1111111', N'ornagur@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1022, N'aaaaaa', N'bla@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1025, N'1111111', N'''arbelax@gmail.com''')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1031, N'1111111', N'''arbelax@gail.com''')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1032, N'1111111', N'''arbelax@gail.com''')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1034, N'123456', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1035, N'123', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1036, N'123456', N'bla@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1037, N'123456', N'bla@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1055, N'', N'')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1056, N'', N'')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1057, N'Aa123456', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1058, N'Aa123456', N'bla1@gail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1059, N'asfg', N'bla2@gail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1060, N'asdf', N'11111@gail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1061, N'asdasd', N'1122@gail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1062, N'asdasd', N'1122@gail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1063, N'asdasd', N'987@mail.mail')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1064, N'asdfg', N'09877@gail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1065, N'asdasd', N'9876@gail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1066, N'asd', N'987612@gail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1067, N'israel', N'ohad@gail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1068, N'Aa123', N'mail@mail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1069, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1070, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1071, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1072, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1073, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1074, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1075, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1076, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1077, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1078, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1079, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1080, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1081, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1082, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1083, N'asdf', N'ohadgur@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1084, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1085, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1086, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1087, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1088, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1089, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1091, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1092, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1093, N'a123', N'arbelax@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1094, N'a123', N'arbelax@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1095, N'a123', N'arbelax@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1096, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1097, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1098, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1099, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1101, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1102, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1103, N'asdf', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1104, N'1234', N'salsadancingonline@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1105, N'1234', N'salsadancingonline@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1119, N'1234', N'salsadancingonline@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1120, N'1234', N'salsadancingonline@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1122, N'1234', N'salsadancingonline@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1123, N'1234', N'salsadancingonline@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1124, N'1234', N'salsadancingonline@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1125, N'1234', N'salsadancingonline@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1127, N'b123', N'bavias54@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1128, N'b123', N'bavias54@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1129, N'1234', N'salsadancingonline@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1130, N'1234', N'salsadancingonline@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1131, N'1234', N'salsadancingonline@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1132, N'1234', N'salsadancingonline@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1133, N'1234', N'salsadancingonline@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1135, N'h123', N'hen_berdugo@walla.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1136, N'h123', N'hen_berdugo@walla.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1137, N'h123', N'hen_berdugo@walla.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1138, N'1234', N'salsadancingonline@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1139, N'1234', N'dalsadancingonline@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1140, N'a123', N'arbelax@gmail.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1141, N'1234', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1142, N'1234', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1143, N'1234', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1144, N'1234', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1145, N'1234', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1146, N'asdf', N'maorsegal6@walla.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1147, N'asdf', N'maorsegal6@walla.com')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1148, N'1234', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1149, N'1234', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1150, N'1234', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1151, N'1234', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1152, N'1234', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1153, N'1234', N'bla')
GO
print 'Processed 100 total records'
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1154, N'1234', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1157, N'asdf', N'dsfj@df')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1158, N'1234', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1159, N'1234', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1160, N'1234', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1161, N'asdf', N'dfggfd@dfd')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1162, N'', N'')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1164, N'1234', N'bla')
INSERT [dbo].[MembersLoginDetails] ([community_member_id], [password], [email_address]) VALUES (1165, N'1234', N'bla')
/****** Object:  Table [dbo].[M_MedicalConditions]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[M_MedicalConditions](
	[medical_condition_id] [int] NOT NULL IDENTITY(10000,1),
	[medical_condition_description] [varchar](100) NULL,
 CONSTRAINT [PK_M_MedicalConditions] PRIMARY KEY CLUSTERED 
(
	[medical_condition_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[M_MedicalConditions] ON
GO
INSERT [dbo].[M_MedicalConditions] ([medical_condition_id], [medical_condition_description]) VALUES (1000, N'had..')
/****** Object:  Table [dbo].[UpdatesDB]    Script Date: 08/08/2015 15:40:06 ******/
GO
SET IDENTITY_INSERT [dbo].[M_MedicalConditions] OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UpdatesDB](
	[id] [int] NOT NULL,
	[json] [int] NOT NULL,
	[community_member_id] [int] NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[testDates]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[testDates](
	[date] [date] NULL,
	[timestamp] [timestamp] NOT NULL,
	[datetime] [datetime] NULL
) ON [PRIMARY]
GO
INSERT [dbo].[testDates] ([date], [datetime]) VALUES (CAST(0x073A0B00 AS Date), NULL)
INSERT [dbo].[testDates] ([date], [datetime]) VALUES (NULL, CAST(0x0000A4AC0048DB53 AS DateTime))
/****** Object:  Table [dbo].[test2]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[test2](
	[id] [int] IDENTITY(1000,1) NOT NULL,
	[name] [nvarchar](10) NULL,
 CONSTRAINT [PK__test2__3213E83F6BE40491] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[test2] ON
INSERT [dbo].[test2] ([id], [name]) VALUES (1000, N'ohad')
INSERT [dbo].[test2] ([id], [name]) VALUES (1001, N'ohad')
INSERT [dbo].[test2] ([id], [name]) VALUES (1002, N'ohad')
SET IDENTITY_INSERT [dbo].[test2] OFF
/****** Object:  Table [dbo].[test1]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[test1](
	[id] [int] NULL,
	[name] [nvarchar](10) NULL
) ON [PRIMARY]
GO
INSERT [dbo].[test1] ([id], [name]) VALUES (1000, N'ohad')
INSERT [dbo].[test1] ([id], [name]) VALUES (1001, N'ohad')
INSERT [dbo].[test1] ([id], [name]) VALUES (1002, N'ohad')
/****** Object:  Table [dbo].[ServerUserNames]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ServerUserNames](
	[user_name] [varchar](30) NOT NULL,
	[password] [varchar](30) NOT NULL,
 CONSTRAINT [PK_ServerUserNames] PRIMARY KEY CLUSTERED 
(
	[user_name] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[RejectCodes]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[RejectCodes](
	[id] [int] NOT NULL IDENTITY(10000,1),
	[description] [varchar](100) NOT NULL,
 CONSTRAINT [PK_RejectCodes] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[RejectCodes] ON
GO
INSERT [dbo].[RejectCodes] ([id], [description]) VALUES (1000, N'dont know patient')
/****** Object:  Table [dbo].[RegistrationFields]    Script Date: 08/08/2015 15:40:06 ******/
GO
SET IDENTITY_INSERT [dbo].[RejectCodes] OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[RegistrationFields](
	[field_name] [varchar](100) NOT NULL,
	[type] [int] NOT NULL,
	[user_type] [int] NOT NULL,
	[fields_group] [int] NOT NULL,
	[needs_verification] [bit] NOT NULL,
	[is_required] [bit] NOT NULL,
	[max_length] [int] NOT NULL,
	[get_possible_values_from] [varchar](50) NULL,
	[serial_num] [int] NOT NULL,
	[insert_data_to] [varchar](200) NULL,
	[gui_description] [varchar](200) NULL,
	[refresh_time] [int] NULL,
 CONSTRAINT [PK__Registra__77D7749045BE5BA9] PRIMARY KEY CLUSTERED 
(
	[field_name] ASC,
	[user_type] ASC,
	[type] ASC,
	[fields_group] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'birth_date', 3, 0, 0, 1, 1, 50, NULL, 3, N'p_communityMembers', N'Birth Date', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'birth_date', 3, 1, 0, 1, 1, 50, NULL, 3, N'p_communityMembers', N'Birth Date', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'certification_external_id', 1, 1, 1, 1, 1, 50, NULL, 10, NULL, N'Certification ID', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'city', 0, 0, 0, 1, 1, 50, NULL, 4, N'p_communityMembers', N'City', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'city', 0, 1, 0, 1, 1, 50, NULL, 4, N'p_communityMembers', N'City', 100)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'contact_phone', 0, 0, 0, 1, 1, 50, NULL, 4, N'P_EmergencyContact', N'Emergency Contact Phone Number', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'contact_phone', 0, 1, 0, 1, 1, 50, NULL, 4, N'P_EmergencyContact', N'Emergency Contact Phone Number', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'date_to', 3, 0, 1, 1, 1, 50, NULL, 6, NULL, N'Date to - general (prototype)', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'doc_license_number', 1, 1, 1, 1, 1, 50, NULL, 10, NULL, N'Doctor license', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'dosage', 2, 0, 1, 1, 1, 0, NULL, 7, N'', N'Medication Dosage', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'email_address', 0, 0, 0, 1, 1, 50, NULL, 3, N'p_communityMembers', N'Email Address', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'email_address', 0, 1, 0, 1, 1, 50, NULL, 0, N'p_communityMembers', N'Email Address', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'external_id', 0, 0, 0, 1, 1, 50, NULL, 0, N'p_communityMembers', N'ID', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'external_id', 0, 1, 0, 1, 1, 50, NULL, 0, N'p_communityMembers', N'ID', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'external_id_type', 1, 0, 0, 1, 1, 50, N'Enum.P_CommunityMembers.external_id_type', 10, NULL, N'ID type', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'external_id_type', 1, 1, 0, 1, 1, 50, N'Enum.P_CommunityMembers.external_id_type', 10, NULL, N'ID type', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'first_name', 0, 0, 0, 1, 1, 50, NULL, 1, N'p_communityMembers', N'First Name', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'first_name', 0, 1, 0, 1, 1, 50, NULL, 1, N'p_communityMembers', N'First Name', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'gender', 1, 0, 0, 1, 1, 50, N'Enum.p_communityMembers.gender', 3, N'p_communityMembers', N'Gender', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'gender', 1, 1, 0, 1, 1, 50, N'Enum.p_communityMembers.gender', 3, N'p_communityMembers', N'Gender', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'hour_from', 1, 0, 2, 0, 0, 2, NULL, 10, N'Availabilty', N'Availability - From Hour', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'hour_from', 1, 1, 2, 0, 0, 2, NULL, 10, N'Availabilty', N'Availability - From Hour', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'hour_to', 1, 0, 2, 0, 0, 2, NULL, 10, N'Availabilty', N'Availability - To Hour', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'hour_to', 1, 1, 2, 0, 0, 2, NULL, 10, N'Availabilty', N'Availability - To Hour', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'last_name', 0, 0, 0, 1, 1, 50, NULL, 2, N'p_communityMembers', N'Last Name', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'last_name', 0, 1, 0, 1, 1, 50, NULL, 2, N'p_communityMembers', N'Last Name', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'medical_condition_id', 1, 0, 1, 1, 1, 50, N'm_medicalConditions', 6, N'P_Diagnosis', N'Medical Condition ID', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'medication_num', 1, 0, 1, 1, 1, 50, N'P_Medications', 7, N'P_prescriptions', N'Medication ID', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'minutes_from', 1, 0, 2, 0, 0, 2, NULL, 10, N'Availabilty', N'Availability - From Minutes', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'minutes_from', 1, 1, 2, 0, 0, 2, NULL, 10, N'Availabilty', N'Availability - From Minutes', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'minutes_to', 1, 0, 2, 0, 0, 2, NULL, 10, N'Availabilty', N'Availability - To Minutes', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'minutes_to', 1, 1, 2, 0, 0, 2, NULL, 10, N'Availabilty', N'Availability - To Minutes', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'mobile_phone_number', 0, 0, 0, 1, 1, 50, NULL, 4, N'p_communityMembers', N'Mobile Phone Number', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'mobile_phone_number', 0, 1, 0, 1, 1, 50, NULL, 4, N'p_communityMembers', N'Mobile Phone Number', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'organization_id', 1, 1, 1, 1, 1, 50, N'MP_Organizations', 10, NULL, N'Organization ID', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'P_diagnosis.doc_license_number', 1, 0, 1, 1, 1, 50, NULL, 9, NULL, N'Diagnosing doctor license number', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'P_prescriptions.doc_license_number', 1, 0, 1, 1, 1, 50, NULL, 10, NULL, N'Prescribing doctor license', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'P_supervision.doc_license_number', 1, 0, 1, 1, 1, 50, NULL, 8, NULL, N'Supervising doctor license number', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'password', 0, 0, 0, 1, 1, 50, NULL, 5, N'p_communityMembers', N'Password', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'password', 0, 1, 0, 1, 1, 50, NULL, 5, N'p_communityMembers', N'Password', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'position_num', 1, 1, 1, 1, 1, 50, N'MP_Positions', 10, NULL, N'Position Num', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'specialization_id', 1, 1, 1, 1, 1, 50, N'MP_Specialization', 10, NULL, N'Specialization ID', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'state', 0, 0, 0, 1, 1, 50, NULL, 4, N'p_communityMembers', N'State', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'state', 0, 1, 0, 1, 1, 50, NULL, 4, N'p_communityMembers', N'State', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'street', 0, 0, 0, 1, 1, 50, NULL, 4, N'p_communityMembers', N'Street', NULL)
INSERT [dbo].[RegistrationFields] ([field_name], [type], [user_type], [fields_group], [needs_verification], [is_required], [max_length], [get_possible_values_from], [serial_num], [insert_data_to], [gui_description], [refresh_time]) VALUES (N'street', 0, 1, 0, 1, 1, 50, NULL, 4, N'p_communityMembers', N'Street', NULL)
INSERT INTO RegistrationFields(field_name, type, user_type, fields_group, needs_verification, is_required, max_length, get_possible_values_from, serial_num, insert_data_to, gui_description, refresh_time) VALUES ('birth_date', 3, 3, 0, 1, 1, 50, null, 3, 'p_communityMembers', 'Birth Date', null);
INSERT INTO RegistrationFields(field_name, type, user_type, fields_group, needs_verification, is_required, max_length, get_possible_values_from, serial_num, insert_data_to, gui_description, refresh_time) VALUES ('city', 0, 3, 0, 1, 1, 50, null, 4, 'p_communityMembers', 'City', null);
INSERT INTO RegistrationFields(field_name, type, user_type, fields_group, needs_verification, is_required, max_length, get_possible_values_from, serial_num, insert_data_to, gui_description, refresh_time) VALUES ('contact_phone', 0, 3, 0, 1, 1, 50, null, 4, 'P_EmergencyContact', 'Emergency Contact Phone Number', null);
INSERT INTO RegistrationFields(field_name, type, user_type, fields_group, needs_verification, is_required, max_length, get_possible_values_from, serial_num, insert_data_to, gui_description, refresh_time) VALUES ('date_to', 3, 3, 1, 1, 1, 50, null, 6, null, 'Date to - general (prototype)', null);
INSERT INTO RegistrationFields(field_name, type, user_type, fields_group, needs_verification, is_required, max_length, get_possible_values_from, serial_num, insert_data_to, gui_description, refresh_time) VALUES ('email_address', 0, 3, 0, 1, 1, 50, null, 3, 'p_communityMembers', 'Email Address', null);
INSERT INTO RegistrationFields(field_name, type, user_type, fields_group, needs_verification, is_required, max_length, get_possible_values_from, serial_num, insert_data_to, gui_description, refresh_time) VALUES ('external_id', 0, 3, 0, 1, 1, 50, null, 0, 'p_communityMembers', 'ID', null);
INSERT INTO RegistrationFields(field_name, type, user_type, fields_group, needs_verification, is_required, max_length, get_possible_values_from, serial_num, insert_data_to, gui_description, refresh_time) VALUES ('external_id_type', 1, 3, 0, 1, 1, 50, 'Enum.P_CommunityMembers.external_id_type', 10, null, 'ID type', null);
INSERT INTO RegistrationFields(field_name, type, user_type, fields_group, needs_verification, is_required, max_length, get_possible_values_from, serial_num, insert_data_to, gui_description, refresh_time) VALUES ('first_name', 0, 3, 0, 1, 1, 50, null, 1, 'p_communityMembers', 'First Name', null);
INSERT INTO RegistrationFields(field_name, type, user_type, fields_group, needs_verification, is_required, max_length, get_possible_values_from, serial_num, insert_data_to, gui_description, refresh_time) VALUES ('gender', 1, 3, 0, 1, 1, 50, 'Enum.p_communityMembers.gender', 3, 'p_communityMembers', 'Gender', null);
INSERT INTO RegistrationFields(field_name, type, user_type, fields_group, needs_verification, is_required, max_length, get_possible_values_from, serial_num, insert_data_to, gui_description, refresh_time) VALUES ('hour_from', 1, 3, 2, 0, 0, 2, null, 10, 'Availabilty', 'Availability - From Hour', null);
INSERT INTO RegistrationFields(field_name, type, user_type, fields_group, needs_verification, is_required, max_length, get_possible_values_from, serial_num, insert_data_to, gui_description, refresh_time) VALUES ('hour_to', 1, 3, 2, 0, 0, 2, null, 10, 'Availabilty', 'Availability - To Hour', null);
INSERT INTO RegistrationFields(field_name, type, user_type, fields_group, needs_verification, is_required, max_length, get_possible_values_from, serial_num, insert_data_to, gui_description, refresh_time) VALUES ('last_name', 0, 3, 0, 1, 1, 50, null, 2, 'p_communityMembers', 'Last Name', null);
INSERT INTO RegistrationFields(field_name, type, user_type, fields_group, needs_verification, is_required, max_length, get_possible_values_from, serial_num, insert_data_to, gui_description, refresh_time) VALUES ('minutes_from', 1, 3, 2, 0, 0, 2, null, 10, 'Availabilty', 'Availability - From Minutes', null);
INSERT INTO RegistrationFields(field_name, type, user_type, fields_group, needs_verification, is_required, max_length, get_possible_values_from, serial_num, insert_data_to, gui_description, refresh_time) VALUES ('minutes_to', 1, 3, 2, 0, 0, 2, null, 10, 'Availabilty', 'Availability - To Minutes', null);
INSERT INTO RegistrationFields(field_name, type, user_type, fields_group, needs_verification, is_required, max_length, get_possible_values_from, serial_num, insert_data_to, gui_description, refresh_time) VALUES ('mobile_phone_number', 0, 3, 0, 1, 1, 50, null, 4, 'p_communityMembers', 'Mobile Phone Number', null);
INSERT INTO RegistrationFields(field_name, type, user_type, fields_group, needs_verification, is_required, max_length, get_possible_values_from, serial_num, insert_data_to, gui_description, refresh_time) VALUES ('password', 0, 3, 0, 1, 1, 50, null, 5, 'p_communityMembers', 'Password', null);
INSERT INTO RegistrationFields(field_name, type, user_type, fields_group, needs_verification, is_required, max_length, get_possible_values_from, serial_num, insert_data_to, gui_description, refresh_time) VALUES ('state', 0, 3, 0, 1, 1, 50, null, 4, 'p_communityMembers', 'State', null);
INSERT INTO RegistrationFields(field_name, type, user_type, fields_group, needs_verification, is_required, max_length, get_possible_values_from, serial_num, insert_data_to, gui_description, refresh_time) VALUES ('street', 0, 3, 0, 1, 1, 50, null, 4, 'p_communityMembers', 'Street', null);
INSERT INTO RegistrationFields(field_name, type, user_type, fields_group, needs_verification, is_required, max_length, get_possible_values_from, serial_num, insert_data_to, gui_description, refresh_time) VALUES ('organization_id', 1, 3, 1, 1, 1, 50, 'MP_Organizations', 10, null, 'Organization ID', null);
INSERT INTO RegistrationFields(field_name, type, user_type, fields_group, needs_verification, is_required, max_length, get_possible_values_from, serial_num, insert_data_to, gui_description, refresh_time) VALUES ('position_num', 1, 3, 1, 1, 1, 50, 'MP_Positions', 10, null, 'Position Num', null);
/****** Object:  Table [dbo].[RegIDs]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[RegIDs](
	[reg_id] [varchar](200) NOT NULL,
	[community_member_id] [int] NOT NULL,
 CONSTRAINT [PK__RegIDs__74038772489AC854] PRIMARY KEY CLUSTERED 
(
	[reg_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[RefreshDetailsTime]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[RefreshDetailsTime](
	[community_member_id] [int] NOT NULL,
	[field_name] [varchar](30) NOT NULL,
	[last_update_time] [datetime] NOT NULL,
	[urgent] [bit] NULL,
 CONSTRAINT [PK__RefreshD__5DDD3A674B7734FF] PRIMARY KEY CLUSTERED 
(
	[community_member_id] ASC,
	[field_name] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[RefreshDetailsTime] ([community_member_id], [field_name], [last_update_time], [urgent]) VALUES (1002, N'MedicationName', CAST(0x000007BD00000000 AS DateTime), 0)
INSERT [dbo].[RefreshDetailsTime] ([community_member_id], [field_name], [last_update_time], [urgent]) VALUES (1030, N'city    ', CAST(0x0000A413010202BE AS DateTime), NULL)
/****** Object:  Table [dbo].[P_Patients]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[P_Patients](
	[patient_id] [int] NOT NULL IDENTITY(10000,1),
	[community_member_id] [int] NOT NULL,
 CONSTRAINT [PK_P_Patients] PRIMARY KEY CLUSTERED 
(
	[patient_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[P_Patients] ON
GO
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1000, 1002)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1002, 1078)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1003, 1079)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1004, 1080)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1005, 1081)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1006, 1082)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1007, 1089)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1008, 1091)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1009, 1092)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1010, 1104)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1011, 1119)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1012, 1120)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1013, 1122)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1014, 1123)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1015, 1124)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1016, 1125)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1017, 1129)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1018, 1130)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1019, 1131)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1020, 1132)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1021, 1133)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1022, 1138)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1023, 1139)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1024, 1141)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1025, 1142)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1026, 1143)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1027, 1144)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1028, 1145)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1029, 1146)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1030, 1147)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1031, 1149)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1032, 1150)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1033, 1151)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1034, 1152)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1035, 1153)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1036, 1154)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1037, 1157)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1038, 1158)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1039, 1159)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1040, 1160)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1041, 1161)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1042, 1164)
INSERT [dbo].[P_Patients] ([patient_id], [community_member_id]) VALUES (1043, 1165)
/****** Object:  Table [dbo].[O_EmergencyEvents]    Script Date: 08/08/2015 15:40:06 ******/
GO
SET IDENTITY_INSERT [dbo].[P_Patients] OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[O_EmergencyEvents](
	[event_id] [int] IDENTITY(1000,1) NOT NULL,
	[create_by_member_id] [int] NOT NULL,
	[patient_id] [int] NOT NULL,
	[medical_condition_id] [int] NOT NULL,
	[created_date] [timestamp] NOT NULL,
	[finished_date] [datetime] NULL,
	[ems_member_id] [int] NULL,
	[status_num] [int] NOT NULL,
	[x] [real] NOT NULL,
	[y] [real] NOT NULL,
	[radius] [real] NULL,
	[state] [varchar](100) NULL,
	[region_type] [int] NULL,
	[location_remark] [varchar](100) NULL,
	[patient_condition_remarks] [varchar](300) NULL,
	[last_action_time] [datetime] NULL,
	[time_to_next_reminder] [int] NULL,
	[memo] [varchar](300) NULL,
 CONSTRAINT [PK__O_Emerge__2370F727571DF1D5] PRIMARY KEY CLUSTERED 
(
	[event_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[P_TypeLog]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[P_TypeLog](
	[type_history_num] [int] NOT NULL IDENTITY(10000,1),
	[user_type] [int] NOT NULL,
	[community_member_id] [int] NOT NULL,
	[date_from] [datetime] NOT NULL default current_timestamp,
	[date_to] [datetime] NULL,
 CONSTRAINT [PK__P_TypeLo__80B026CF4E53A1AA] PRIMARY KEY CLUSTERED 
(
	[type_history_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[P_TypeLog] ON
GO
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1000, 0, 1002, CAST(0x0000A48A009DC22D AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1001, 0, 1002, CAST(0x0000A48A00C5B368 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1002, 1, 1003, CAST(0x0000A48A00C5BB5F AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1003, 0, 1022, CAST(0x0000A4B100B5F7D5 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1005, 1, 1031, CAST(0x0000A9B400000000 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1006, 1, 1032, CAST(0x0000A9B400000000 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1011, 1, 1057, CAST(0x0000A4BB007AEE5A AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1012, 1, 1063, CAST(0x0000A4BB007CB7FA AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1013, 1, 1064, CAST(0x0000A4BB0087B454 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1014, 1, 1065, CAST(0x0000A4BB008BA6F2 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1015, 1, 1066, CAST(0x0000A4BB008F8651 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1016, 1, 1067, CAST(0x0000A4BB009589BC AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1017, 1, 1068, CAST(0x0000A4BB00A34DC9 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1018, 1, 1069, CAST(0x0000A4BB00B133D7 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1019, 1, 1070, CAST(0x0000A4BB00B92D82 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1020, 1, 1071, CAST(0x0000A4BB00B92EAA AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1021, 1, 1072, CAST(0x0000A4BB00BC3475 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1022, 1, 1073, CAST(0x0000A4BB00BE7D67 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1023, 1, 1074, CAST(0x0000A4BB00BE7D96 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1024, 1, 1075, CAST(0x0000A4BB00C4C969 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1025, 1, 1076, CAST(0x0000A4BB00D692A5 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1026, 1, 1077, CAST(0x0000A4BB00D83BA3 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1027, 0, 1078, CAST(0x0000A4BE0070D576 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1028, 0, 1079, CAST(0x0000A4BE0077F235 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1029, 0, 1080, CAST(0x0000A4BE007B5F23 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1030, 0, 1081, CAST(0x0000A4BE00826AB5 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1031, 0, 1082, CAST(0x0000A4BE008DA589 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1032, 1, 1083, CAST(0x0000A4BF004B4F9A AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1033, 1, 1084, CAST(0x0000A4BF0095097D AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1034, 1, 1085, CAST(0x0000A4BF0099F839 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1035, 1, 1086, CAST(0x0000A4BF009CC674 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1036, 1, 1087, CAST(0x0000A4BF00A26DA1 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1037, 1, 1088, CAST(0x0000A4BF00A717CA AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1038, 0, 1089, CAST(0x0000A4C0005FC218 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1039, 0, 1091, CAST(0x0000A4C000647C46 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1040, 0, 1092, CAST(0x0000A4C00065D46C AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1041, 1, 1093, CAST(0x0000A4C0008A0FF5 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1042, 1, 1094, CAST(0x0000A4C000928DCE AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1043, 1, 1095, CAST(0x0000A4C000ACCDB8 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1044, 1, 1096, CAST(0x0000A4C100BA7937 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1045, 1, 1097, CAST(0x0000A4C100D659D7 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1046, 1, 1098, CAST(0x0000A4C100F6DF9C AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1047, 1, 1099, CAST(0x0000A4C100F903E7 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1048, 1, 1101, CAST(0x0000A4C100FC054F AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1049, 1, 1102, CAST(0x0000A4C20040492D AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1050, 1, 1103, CAST(0x0000A4C20041AF40 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1051, 0, 1104, CAST(0x0000A4C200A8BF58 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1052, 0, 1119, CAST(0x0000A4C30042C0BF AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1053, 0, 1120, CAST(0x0000A4C300433F41 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1054, 0, 1122, CAST(0x0000A4C30043710F AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1055, 0, 1123, CAST(0x0000A4C30043D1D4 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1056, 0, 1124, CAST(0x0000A4C3004705A6 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1057, 0, 1125, CAST(0x0000A4C30047CF6E AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1058, 1, 1127, CAST(0x0000A4C300665512 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1059, 1, 1128, CAST(0x0000A4C5002EB8EA AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1060, 0, 1129, CAST(0x0000A4C500542086 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1061, 0, 1130, CAST(0x0000A4C500555936 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1062, 0, 1131, CAST(0x0000A4C500592747 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1063, 0, 1132, CAST(0x0000A4C50059EC80 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1064, 0, 1133, CAST(0x0000A4C5006171BA AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1065, 1, 1136, CAST(0x0000A4C60096D7D5 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1066, 1, 1135, CAST(0x0000A4C60096D7D5 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1067, 1, 1137, CAST(0x0000A4C60096D7DF AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1068, 0, 1138, CAST(0x0000A4C600A127FA AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1069, 0, 1139, CAST(0x0000A4C600A18EC5 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1070, 1, 1140, CAST(0x0000A4C600A19969 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1071, 0, 1141, CAST(0x0000A4C600BB81C4 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1072, 0, 1142, CAST(0x0000A4C600BB9C8D AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1073, 0, 1143, CAST(0x0000A4C600BCA49C AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1074, 0, 1144, CAST(0x0000A4C600BE987D AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1075, 0, 1145, CAST(0x0000A4C600C05C52 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1076, 0, 1146, CAST(0x0000A4C600C4828D AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1077, 0, 1147, CAST(0x0000A4C600C6BA57 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1078, 0, 1149, CAST(0x0000A4C600CD6A6F AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1079, 0, 1150, CAST(0x0000A4C600CFBB75 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1080, 0, 1151, CAST(0x0000A4C600D190A5 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1081, 0, 1152, CAST(0x0000A4C600D4FFF9 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1082, 0, 1153, CAST(0x0000A4C600E051E9 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1083, 0, 1154, CAST(0x0000A4C700BB539F AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1084, 0, 1157, CAST(0x0000A4C700C325CE AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1085, 0, 1158, CAST(0x0000A4C700C4B969 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1086, 0, 1159, CAST(0x0000A4C700C71C7C AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1087, 0, 1160, CAST(0x0000A4C700C98225 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1088, 0, 1161, CAST(0x0000A4C700DCA373 AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1089, 0, 1164, CAST(0x0000A4C8007E852B AS DateTime), NULL)
INSERT [dbo].[P_TypeLog] ([type_history_num], [user_type], [community_member_id], [date_from], [date_to]) VALUES (1090, 0, 1165, CAST(0x0000A4C80080C326 AS DateTime), NULL)
/****** Object:  Table [dbo].[M_Indications]    Script Date: 08/08/2015 15:40:06 ******/
GO
SET IDENTITY_INSERT [dbo].[P_TypeLog] OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[M_Indications](
	[indication_num] [int] NOT NULL IDENTITY(10000,1),
	[medical_condition_id] [int] NOT NULL,
	[brand_name_id] [int] NOT NULL,
 CONSTRAINT [PK_M_Indications] PRIMARY KEY CLUSTERED 
(
	[indication_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[M_Compositions]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[M_Compositions](
	[composiotion_num] [int] NOT NULL IDENTITY(10000,1),
	[active_component_id] [int] NOT NULL,
	[brand_name_id] [int] NOT NULL,
	[quantity] [int] NOT NULL,
	[unit_of_measure] [int] NOT NULL,
 CONSTRAINT [PK_M_Compositions] PRIMARY KEY CLUSTERED 
(
	[composiotion_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[M_Substitutives]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[M_Substitutives](
	[substitutive_num] [int] NOT NULL IDENTITY(10000,1),
	[brand_name_id1] [int] NOT NULL,
	[brand_name_id2] [int] NOT NULL,
	[conversion_ratio] [float] NOT NULL,
	[compliance] [varchar](10) NOT NULL,
 CONSTRAINT [PK__M_Substi__43C5263F3F115E1A] PRIMARY KEY CLUSTERED 
(
	[substitutive_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[P_Doctors]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[P_Doctors](
	[doctor_id] [int] NOT NULL IDENTITY(10000,1),
	[first_name] [varchar](30) NOT NULL,
	[last_name] [varchar](30) NOT NULL,
	[doc_license_number] [int] NOT NULL,
	[community_member_id] [int] NOT NULL,
 CONSTRAINT [PK_P_Doctors] PRIMARY KEY CLUSTERED 
(
	[doctor_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[P_Doctors] ON
GO
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1000, N'avi', N'asulin', 10054, 1003)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1001, N'Arbel', N'Axelrod', 123456, 1031)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1002, N'Arbel', N'Axelrod', 123456, 1032)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1003, N'asd', N'dsa', 5678, 1063)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1004, N'as', N'dsa', 6789, 1064)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1005, N'asd', N'asd', 87654, 1065)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1006, N'asd', N'dsa', 123455, 1066)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1007, N'asd', N'dsa', 8776, 1067)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1008, N'asd', N'dsa', 7653, 1068)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1009, N'asd', N'dsa', 7654, 1069)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1010, N'asd', N'dsa', 12345, 1070)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1011, N'asd', N'dsa', 12345, 1071)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1012, N'asd', N'dsa', 3456, 1072)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1013, N'asd', N'dsa', 123123, 1073)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1014, N'asd', N'dsa', 123123, 1074)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1015, N'asd', N'dsa', 876875, 1075)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1016, N'asd', N'dsa', 32423, 1076)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1017, N'asd', N'dsa', 236, 1077)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1018, N'Ohad', N'Gur', 1234, 1083)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1019, N'Ohad', N'Gur', 234234, 1084)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1020, N'asd', N'dsa', 34567, 1085)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1021, N'asd', N'dsa', 1234, 1086)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1022, N'asd', N'dsa', 1000, 1087)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1023, N'asd', N'dsa', 1234, 1088)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1024, N'Arbel', N'Axelrod', 111, 1093)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1025, N'Arbel', N'Axelrod', 111, 1094)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1026, N'Arbel', N'Axelrod', 111, 1095)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1027, N'asd', N'gur', 246, 1096)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1028, N'Ohad', N'Gur', 678, 1097)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1029, N'new', N'doctor', 8643, 1098)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1030, N'new', N'doctor', 94095, 1099)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1031, N'asd', N'asdasd', 87654, 1101)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1032, N'asd', N'dsa', 745747, 1103)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1033, N'avia', N'ber', 222, 1127)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1034, N'avia', N'ber', 222, 1128)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1035, N'hen', N'berdugo', 555, 1136)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1036, N'hen', N'berdugo', 555, 1135)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1037, N'hen', N'berdugo', 555, 1137)
INSERT [dbo].[P_Doctors] ([doctor_id], [first_name], [last_name], [doc_license_number], [community_member_id]) VALUES (1038, N'arbel', N'axelrod', 111, 1140)
/****** Object:  Table [dbo].[Frequencies]    Script Date: 08/08/2015 15:40:06 ******/
GO
SET IDENTITY_INSERT [dbo].[P_Doctors] OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Frequencies](
	[name] [varchar](30) NOT NULL,
	[frequency] [real] NOT NULL,
	[medical_condition_id] [int] NULL,
	[state] [varchar](50) NOT NULL,
	[area] [varchar](50) NULL,
	[patient_age] [int] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[Frequencies] ([name], [frequency], [medical_condition_id], [state], [area], [patient_age]) VALUES (N'connect_server_frequency', 49, 1000, N'Israel', N'any', 30)
INSERT [dbo].[Frequencies] ([name], [frequency], [medical_condition_id], [state], [area], [patient_age]) VALUES (N'frequency in emergency', 30, 1000, N'Israel', N'any', 20)
INSERT [dbo].[Frequencies] ([name], [frequency], [medical_condition_id], [state], [area], [patient_age]) VALUES (N'location_frequency', 43, 1000, N'Israel', N'any', 40)
INSERT [dbo].[Frequencies] ([name], [frequency], [medical_condition_id], [state], [area], [patient_age]) VALUES (N'times_to_conect_to_serve', 55, 1000, N'Israel', N'any', 30)
/****** Object:  Table [dbo].[B_DecisionGivenAmbulaneETA]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[B_DecisionGivenAmbulaneETA](
	[medical_condition_id] [int] NOT NULL,
	[state] [varchar](50) NOT NULL,
	[patient_age] [varchar](50) NOT NULL,
	[ems_eta] [varchar](50) NOT NULL,
	[use_erc] [bit] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Availability]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Availability](
	[hour_from] [int] NOT NULL,
	[minutes_from] [int] NOT NULL,
	[hour_to] [int] NOT NULL,
	[minutes_to] [int] NOT NULL,
	[community_member_id] [int] NOT NULL,
 CONSTRAINT [PK__Availabi__CA3297CC35BCFE0A] PRIMARY KEY CLUSTERED 
(
	[community_member_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (12, 56, 16, 52, 1031)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (12, 56, 16, 52, 1032)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (8, 0, 10, 0, 1060)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (8, 0, 12, 0, 1061)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (8, 0, 12, 0, 1062)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (9, 0, 10, 0, 1063)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (9, 0, 10, 0, 1064)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (1, 0, 2, 0, 1065)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (1, 12, 3, 12, 1066)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (12, 0, 19, 0, 1067)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (9, 10, 10, 11, 1068)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (20, 0, 24, 0, 1069)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (11, 0, 19, 0, 1070)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (11, 0, 19, 0, 1071)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (1, 0, 10, 0, 1072)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (9, 0, 11, 0, 1073)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (9, 0, 11, 0, 1074)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (10, 0, 12, 0, 1075)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (20, 0, 22, 0, 1076)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (22, 0, 23, 0, 1077)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (10, 0, 12, 0, 1078)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (10, 0, 12, 0, 1079)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (10, 0, 22, 0, 1080)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (10, 0, 20, 0, 1081)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (22, 15, 23, 13, 1082)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (10, 0, 14, 0, 1083)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (10, 0, 12, 0, 1084)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (10, 0, 12, 0, 1085)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (11, 0, 12, 0, 1086)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (12, 0, 13, 0, 1087)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (10, 0, 12, 0, 1088)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (12, 0, 13, 0, 1089)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (12, 0, 13, 0, 1091)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (12, 0, 13, 0, 1092)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (11, 11, 11, 11, 1093)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (11, 11, 11, 11, 1094)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (11, 11, 11, 11, 1095)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (11, 17, 12, 18, 1096)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (10, 0, 19, 0, 1097)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (10, 0, 15, 0, 1098)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (10, 0, 15, 0, 1099)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (10, 0, 12, 0, 1101)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (10, 0, 18, 0, 1102)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (10, 0, 12, 0, 1103)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (12, 12, 12, 12, 1104)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (22, 22, 22, 22, 1119)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (22, 22, 22, 22, 1120)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (22, 22, 22, 22, 1122)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (22, 22, 22, 22, 1123)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (22, 22, 22, 22, 1124)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (22, 22, 22, 22, 1125)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (8, 2, 13, 2, 1127)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (8, 2, 13, 2, 1128)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (22, 22, 22, 22, 1129)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (22, 22, 22, 22, 1130)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (22, 22, 22, 22, 1131)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (22, 22, 22, 22, 1132)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (22, 22, 22, 22, 1133)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (8, 1, 16, 1, 1135)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (8, 1, 16, 1, 1136)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (8, 1, 16, 1, 1137)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (22, 22, 22, 22, 1138)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (22, 22, 22, 22, 1139)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (8, 8, 9, 9, 1140)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (12, 12, 12, 12, 1141)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (12, 12, 12, 12, 1142)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (11, 11, 11, 11, 1143)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (11, 11, 11, 11, 1144)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (11, 11, 11, 11, 1145)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (10, 0, 12, 0, 1146)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (10, 0, 12, 0, 1147)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (11, 11, 11, 11, 1149)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (11, 11, 11, 11, 1150)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (11, 11, 11, 11, 1151)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (11, 11, 11, 11, 1152)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (11, 11, 11, 11, 1153)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (11, 11, 11, 11, 1154)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (10, 0, 18, 0, 1157)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (10, 10, 10, 10, 1158)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (11, 1, 10, 1, 1159)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (1, 1, 1, 1, 1160)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (10, 0, 12, 0, 1161)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (10, 10, 10, 10, 1164)
INSERT [dbo].[Availability] ([hour_from], [minutes_from], [hour_to], [minutes_to], [community_member_id]) VALUES (10, 10, 10, 10, 1165)
/****** Object:  Table [dbo].[MP_Organizations]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[MP_Organizations](
	[organization_id] [int] NOT NULL IDENTITY(10000,1),
	[organization_description] [varchar](50) NOT NULL,
	[organization_type_num] [int] NOT NULL,
	[org_state] [varchar](50) NOT NULL,
	[org_city] [varchar](50) NOT NULL,
	[org_street] [varchar](50) NOT NULL,
	[org_house] [int] NOT NULL,
	[org_phone_number] [varchar](50) NOT NULL,
	[fax_number] [varchar](50) NOT NULL,
	[email_address_of_organization] [varchar](100) NOT NULL,
	[web_site] [varchar](100) NOT NULL,
	[remarks] [varchar](100) NULL,
 CONSTRAINT [PK_MP_Organizations] PRIMARY KEY CLUSTERED 
(
	[organization_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[MP_Organizations] ON
GO
INSERT [dbo].[MP_Organizations] ([organization_id], [organization_description], [organization_type_num], [org_state], [org_city], [org_street], [org_house], [org_phone_number], [fax_number], [email_address_of_organization], [web_site], [remarks]) VALUES (1000, N'macabi', 1000, N'Israel', N'Ramat-Gan', N'binyamin', 4, N'038574685', N'034857636', N'mac@gmail.com', N'mac.org.il', N'best')
INSERT [dbo].[MP_Organizations] ([organization_id], [organization_description], [organization_type_num], [org_state], [org_city], [org_street], [org_house], [org_phone_number], [fax_number], [email_address_of_organization], [web_site], [remarks]) VALUES (1006, N'Tel Hashomer', 1000, N'Israel', N'Ramat Gan', N'Tel Hashomer', 1, N'03-1111111', N'03-2222222', N'tel_hashomer@tel_hashomer.co.il', N'tel.hashomer.com', NULL)
INSERT [dbo].[MP_Organizations] ([organization_id], [organization_description], [organization_type_num], [org_state], [org_city], [org_street], [org_house], [org_phone_number], [fax_number], [email_address_of_organization], [web_site], [remarks]) VALUES (1007, N'Rabin', 1000, N'Israel', N'Petach Tikva', N'Rabin', 1, N'03-1111111', N'03-2222222', N'Rabin@Rabin.co.il', N'rabin.rabin.com', NULL)
/****** Object:  Table [dbo].[MP_MedicalPersonnel]    Script Date: 08/08/2015 15:40:06 ******/
GO
SET IDENTITY_INSERT [dbo].[MP_Organizations] OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[MP_MedicalPersonnel](
	[medical_personnel_id] [int] NOT NULL IDENTITY(10000,1),
	[community_member_id] [int] NOT NULL,
 CONSTRAINT [PK__MP_Medic__0C9561893D5E1FD2] PRIMARY KEY CLUSTERED 
(
	[medical_personnel_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[MP_MedicalPersonnel] ON
GO
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1000, 1003)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1001, 1065)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1002, 1066)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1003, 1067)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1004, 1068)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1005, 1069)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1006, 1070)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1007, 1071)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1008, 1072)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1009, 1073)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1010, 1074)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1011, 1075)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1012, 1076)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1013, 1077)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1014, 1083)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1015, 1084)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1016, 1085)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1017, 1086)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1018, 1087)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1019, 1088)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1020, 1093)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1021, 1094)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1022, 1095)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1023, 1096)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1024, 1097)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1025, 1098)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1026, 1099)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1027, 1101)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1028, 1103)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1029, 1127)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1030, 1128)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1031, 1136)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1032, 1135)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1033, 1137)
INSERT [dbo].[MP_MedicalPersonnel] ([medical_personnel_id], [community_member_id]) VALUES (1034, 1140)
/****** Object:  Table [dbo].[MP_Certification]    Script Date: 08/08/2015 15:40:06 ******/
GO
SET IDENTITY_INSERT [dbo].[MP_MedicalPersonnel] OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[MP_Certification](
	[certification_internal_id] [int] NOT NULL IDENTITY(10000,1),
	[certification_external_id] [int] NOT NULL,
	[medical_personnel_id] [int] NOT NULL,
	[c_date_from] [date] NOT NULL default current_timestamp,
	[c_date_to] [date] NULL,
	[specialization_id] [int] NOT NULL,
 CONSTRAINT [PK_MP_Certification] PRIMARY KEY CLUSTERED 
(
	[certification_internal_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[MP_Certification] ON
GO
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1000, 554, 1000, CAST(0xE5390B00 AS Date), NULL, 1000)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1001, 9876, 1001, CAST(0x163A0B00 AS Date), NULL, 1000)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1002, 87437, 1002, CAST(0x163A0B00 AS Date), NULL, 1000)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1003, 6789, 1003, CAST(0x163A0B00 AS Date), NULL, 1006)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1004, 987, 1004, CAST(0x163A0B00 AS Date), NULL, 1000)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1005, 98766, 1005, CAST(0x163A0B00 AS Date), NULL, 1007)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1006, 8765, 1006, CAST(0x163A0B00 AS Date), NULL, 1009)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1007, 8765, 1007, CAST(0x163A0B00 AS Date), NULL, 1009)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1008, 766, 1008, CAST(0x163A0B00 AS Date), NULL, 1000)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1009, 9845345, 1009, CAST(0x163A0B00 AS Date), NULL, 1000)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1010, 9845345, 1010, CAST(0x163A0B00 AS Date), NULL, 1000)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1011, 875, 1011, CAST(0x163A0B00 AS Date), NULL, 1000)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1012, 45435, 1012, CAST(0x163A0B00 AS Date), NULL, 1000)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1013, 47667, 1013, CAST(0x163A0B00 AS Date), NULL, 1000)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1014, 1234, 1014, CAST(0x1A3A0B00 AS Date), NULL, 1018)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1015, 6654, 1015, CAST(0x1A3A0B00 AS Date), NULL, 1000)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1016, 4545, 1016, CAST(0x1A3A0B00 AS Date), NULL, 1000)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1017, 654, 1017, CAST(0x1A3A0B00 AS Date), NULL, 1000)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1018, 1234, 1018, CAST(0x1A3A0B00 AS Date), NULL, 1000)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1019, 7654, 1019, CAST(0x1A3A0B00 AS Date), NULL, 1002)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1020, 11111, 1020, CAST(0x1B3A0B00 AS Date), NULL, 1018)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1021, 111, 1021, CAST(0x1B3A0B00 AS Date), NULL, 1018)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1022, 111, 1022, CAST(0x1B3A0B00 AS Date), NULL, 1018)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1023, 786876, 1023, CAST(0x1C3A0B00 AS Date), NULL, 1005)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1024, 98765, 1024, CAST(0x1C3A0B00 AS Date), NULL, 1000)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1025, 988, 1025, CAST(0x1C3A0B00 AS Date), NULL, 1000)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1026, 9875, 1026, CAST(0x1C3A0B00 AS Date), NULL, 1000)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1027, 98765, 1027, CAST(0x1C3A0B00 AS Date), NULL, 1000)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1028, 78657, 1028, CAST(0x1D3A0B00 AS Date), NULL, 1000)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1029, 222, 1030, CAST(0x203A0B00 AS Date), NULL, 1007)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1030, 555, 1031, CAST(0x213A0B00 AS Date), NULL, 1007)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1031, 555, 1032, CAST(0x213A0B00 AS Date), NULL, 1007)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1032, 555, 1033, CAST(0x213A0B00 AS Date), NULL, 1007)
INSERT [dbo].[MP_Certification] ([certification_internal_id], [certification_external_id], [medical_personnel_id], [c_date_from], [c_date_to], [specialization_id]) VALUES (1033, 1111, 1034, CAST(0x213A0B00 AS Date), NULL, 1018)
/****** Object:  Table [dbo].[P_StatusLog]    Script Date: 08/08/2015 15:40:06 ******/
GO
SET IDENTITY_INSERT [dbo].[MP_Certification] OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[P_StatusLog](
	[status_history_num] [int] NOT NULL IDENTITY(10000,1),
	[status_num] [int] NOT NULL,
	[community_member_id] [int] NOT NULL,
	[date_from] [datetime] NOT NULL DEFAULT current_timestamp,
	[date_to] [datetime] NULL,
 CONSTRAINT [PK__P_Status__D371C98956E8E7AB] PRIMARY KEY CLUSTERED 
(
	[status_history_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[P_StatusLog] ON
GO
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1000, 1000, 1002, CAST(0x0000A48A009A76B7 AS DateTime), CAST(0x0000A48A00FE0996 AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1001, 1000, 1003, CAST(0x0000A48A00BB8D7B AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1002, 1001, 1002, CAST(0x0000A48A00FE0A72 AS DateTime), CAST(0x0000A48E0045BB7A AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1003, 1000, 1002, CAST(0x0000A48E0045BC56 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1006, 1001, 1012, CAST(0x0000A49A00CAE170 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1007, 1001, 1015, CAST(0x0000A49A00CEA3FC AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1008, 1001, 1018, CAST(0x0000A49B00D5626E AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1009, 1001, 1019, CAST(0x0000A49B00D5A159 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1010, 1001, 1020, CAST(0x0000A49B00D72274 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1011, 1001, 1021, CAST(0x0000A49B00D74A92 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1014, 1001, 1025, CAST(0x0000A4B20085756F AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1020, 1001, 1034, CAST(0x0000A4B200D7BC88 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1021, 1001, 1035, CAST(0x0000A4B300177FAE AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1022, 1001, 1036, CAST(0x0000A4B30021E174 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1023, 1001, 1037, CAST(0x0000A4B30021F3C9 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1024, 1001, 1067, CAST(0x0000A4BB00958C80 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1025, 1001, 1068, CAST(0x0000A4BB00A35092 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1026, 1001, 1069, CAST(0x0000A4BB00B1369B AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1027, 1001, 1070, CAST(0x0000A4BB00B93046 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1028, 1001, 1071, CAST(0x0000A4BB00B9317C AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1029, 1001, 1072, CAST(0x0000A4BB00BC3739 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1030, 1001, 1073, CAST(0x0000A4BB00BE8030 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1031, 1001, 1074, CAST(0x0000A4BB00BE8068 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1032, 1001, 1075, CAST(0x0000A4BB00C4CF24 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1033, 1001, 1076, CAST(0x0000A4BB00D69580 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1034, 1001, 1077, CAST(0x0000A4BB00D83E7F AS DateTime), CAST(0x0000A4BB00D884DE AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1035, 1000, 1077, CAST(0x0000A4BB00D885B6 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1036, 1001, 1081, CAST(0x0000A4BE00826CE7 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1037, 1001, 1082, CAST(0x0000A4BE008DA7AD AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1038, 1001, 1083, CAST(0x0000A4BF004B527A AS DateTime), CAST(0x0000A4BF004C53B9 AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1039, 1000, 1083, CAST(0x0000A4BF004C5490 AS DateTime), CAST(0x0000A4C60069A25C AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1040, 1001, 1087, CAST(0x0000A4BF00A2706E AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1041, 1001, 1088, CAST(0x0000A4BF00A71A97 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1042, 1001, 1089, CAST(0x0000A4C0005FC514 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1044, 1001, 1091, CAST(0x0000A4C000647F0E AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1045, 1001, 1092, CAST(0x0000A4C00065D873 AS DateTime), CAST(0x0000A4C0006619ED AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1046, 1000, 1092, CAST(0x0000A4C000661AC5 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1047, 1001, 1093, CAST(0x0000A4C0008A12C2 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1048, 1001, 1094, CAST(0x0000A4C000929092 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1049, 1001, 1095, CAST(0x0000A4C000ACD07C AS DateTime), CAST(0x0000A4C000ACEC85 AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1050, 1000, 1095, CAST(0x0000A4C000ACED5C AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1051, 1001, 1096, CAST(0x0000A4C100BA7C0D AS DateTime), CAST(0x0000A4C100BAA508 AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1052, 1000, 1096, CAST(0x0000A4C100BAA5DF AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1053, 1001, 1097, CAST(0x0000A4C100D65C9B AS DateTime), CAST(0x0000A4C100D68F53 AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1054, 1000, 1097, CAST(0x0000A4C100D6902B AS DateTime), CAST(0x0000A4C100F708CA AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1056, 1002, 1097, CAST(0x0000A4C100F66FE1 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1057, 1001, 1098, CAST(0x0000A4C100F6E25B AS DateTime), CAST(0x0000A4C100F7616E AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1058, 1002, 1097, CAST(0x0000A4C100F75DC5 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1059, 1000, 1098, CAST(0x0000A4C100F7624F AS DateTime), CAST(0x0000A4C100FC9214 AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1060, 1001, 1099, CAST(0x0000A4C100F906A5 AS DateTime), CAST(0x0000A4C100F924C0 AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1061, 1000, 1099, CAST(0x0000A4C100F92597 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1063, 1001, 1101, CAST(0x0000A4C100FC080E AS DateTime), CAST(0x0000A4C100FC6F8C AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1064, 1000, 1101, CAST(0x0000A4C100FC7063 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1065, 1002, 1098, CAST(0x0000A4C100FC92F1 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1066, 1001, 1103, CAST(0x0000A4C20041B217 AS DateTime), CAST(0x0000A4C20042CF49 AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1067, 1000, 1103, CAST(0x0000A4C20042D026 AS DateTime), CAST(0x0000A4C200C07A53 AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1068, 1002, 1103, CAST(0x0000A4C20042F20B AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1069, 1001, 1104, CAST(0x0000A4C200A8C259 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1074, 1002, 1103, CAST(0x0000A4C200BFC0E2 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1076, 1002, 1103, CAST(0x0000A4C200C0201F AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1077, 1002, 1103, CAST(0x0000A4C200C07B38 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1086, 1001, 1119, CAST(0x0000A4C30042C3C9 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1087, 1001, 1120, CAST(0x0000A4C300434283 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1088, 1001, 1122, CAST(0x0000A4C300437419 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1089, 1001, 1123, CAST(0x0000A4C30043D4D0 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1090, 1001, 1124, CAST(0x0000A4C3004708A2 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1091, 1001, 1125, CAST(0x0000A4C30047D278 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1092, 1002, 1083, CAST(0x0000A4C3007FD6DE AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1093, 1001, 1128, CAST(0x0000A4C5002EBBBC AS DateTime), CAST(0x0000A4C5002FFBD2 AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1094, 1000, 1128, CAST(0x0000A4C5002FFCC1 AS DateTime), CAST(0x0000A4C5003BD5AD AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1095, 1002, 1128, CAST(0x0000A4C50034844A AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1096, 1002, 1128, CAST(0x0000A4C5003AAED2 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1097, 1002, 1128, CAST(0x0000A4C5003BD68A AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1098, 1001, 1129, CAST(0x0000A4C5005423CD AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1099, 1001, 1130, CAST(0x0000A4C500555C33 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1100, 1001, 1131, CAST(0x0000A4C50059392B AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1101, 1001, 1132, CAST(0x0000A4C5005A00F0 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1102, 1001, 1133, CAST(0x0000A4C5006175C6 AS DateTime), CAST(0x0000A4C500622007 AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1103, 1000, 1133, CAST(0x0000A4C5006220E3 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1105, 1002, 1083, CAST(0x0000A4C6005F8808 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1106, 1002, 1083, CAST(0x0000A4C600613E96 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1107, 1002, 1083, CAST(0x0000A4C60061AE8E AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1108, 1002, 1083, CAST(0x0000A4C60062C91B AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1109, 1002, 1083, CAST(0x0000A4C600635473 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1110, 1002, 1083, CAST(0x0000A4C6006393A3 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1111, 1002, 1083, CAST(0x0000A4C60063CBBE AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1112, 1002, 1083, CAST(0x0000A4C600642FF9 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1113, 1002, 1083, CAST(0x0000A4C600644FC3 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1114, 1002, 1083, CAST(0x0000A4C600655DA4 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1115, 1002, 1083, CAST(0x0000A4C600660813 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1116, 1002, 1083, CAST(0x0000A4C600666818 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1117, 1002, 1083, CAST(0x0000A4C60066ED86 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1118, 1002, 1083, CAST(0x0000A4C600677064 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1119, 1002, 1083, CAST(0x0000A4C6006881A3 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1120, 1002, 1083, CAST(0x0000A4C60069A38C AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1121, 1001, 1136, CAST(0x0000A4C60096DA99 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1122, 1001, 1135, CAST(0x0000A4C60096DA99 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1123, 1001, 1137, CAST(0x0000A4C60096DAB0 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1124, 1001, 1138, CAST(0x0000A4C600A12C01 AS DateTime), CAST(0x0000A4C600A17D81 AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1125, 1000, 1138, CAST(0x0000A4C600A17E5E AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1126, 1001, 1139, CAST(0x0000A4C600A191FA AS DateTime), NULL)
GO
print 'Processed 100 total records'
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1127, 1001, 1140, CAST(0x0000A4C600A19C2D AS DateTime), CAST(0x0000A4C600A207C9 AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1128, 1000, 1140, CAST(0x0000A4C600A208A1 AS DateTime), CAST(0x0000A4C600B2322C AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1130, 1002, 1140, CAST(0x0000A4C600B23303 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1131, 1001, 1141, CAST(0x0000A4C600BB84C9 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1132, 1001, 1142, CAST(0x0000A4C600BB9F98 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1133, 1001, 1143, CAST(0x0000A4C600BCA85D AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1134, 1001, 1144, CAST(0x0000A4C600BE9B82 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1135, 1001, 1145, CAST(0x0000A4C600C05F57 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1136, 1001, 1146, CAST(0x0000A4C600C48694 AS DateTime), CAST(0x0000A4C600C4E124 AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1137, 1000, 1146, CAST(0x0000A4C600C4E201 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1138, 1001, 1147, CAST(0x0000A4C600C6BE63 AS DateTime), CAST(0x0000A4C600C6E7E1 AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1139, 1000, 1147, CAST(0x0000A4C600C6E8B9 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1140, 1001, 1149, CAST(0x0000A4C600CD6D6B AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1141, 1001, 1150, CAST(0x0000A4C600CFBE75 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1142, 1001, 1151, CAST(0x0000A4C600D193AF AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1143, 1001, 1152, CAST(0x0000A4C600D50303 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1144, 1001, 1153, CAST(0x0000A4C600E054E0 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1145, 1001, 1154, CAST(0x0000A4C700BB5688 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1146, 1001, 1157, CAST(0x0000A4C700C329C8 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1147, 1001, 1158, CAST(0x0000A4C700C4BC3F AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1148, 1001, 1159, CAST(0x0000A4C700C71F61 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1149, 1001, 1160, CAST(0x0000A4C700C98607 AS DateTime), CAST(0x0000A4C700CA2180 AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1150, 1000, 1160, CAST(0x0000A4C700CA2253 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1151, 1001, 1161, CAST(0x0000A4C700DCA754 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1152, 1001, 1164, CAST(0x0000A4C8007E8916 AS DateTime), CAST(0x0000A4C8007FCFD1 AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1153, 1000, 1164, CAST(0x0000A4C8007FD0A0 AS DateTime), CAST(0x0000A4C80093520A AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1154, 1001, 1165, CAST(0x0000A4C80080C708 AS DateTime), CAST(0x0000A4C800810988 AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1155, 1000, 1165, CAST(0x0000A4C800810A5B AS DateTime), CAST(0x0000A4C800822E8F AS DateTime))
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1156, 1002, 1165, CAST(0x0000A4C800822F66 AS DateTime), NULL)
INSERT [dbo].[P_StatusLog] ([status_history_num], [status_num], [community_member_id], [date_from], [date_to]) VALUES (1157, 1002, 1164, CAST(0x0000A4C8009352E1 AS DateTime), NULL)
/****** Object:  Table [dbo].[P_Relations]    Script Date: 08/08/2015 15:40:06 ******/
GO
SET IDENTITY_INSERT [dbo].[P_StatusLog] OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[P_Relations](
	[relation_num] [int] NOT NULL IDENTITY(10000,1),
	[community_member_id] [int] NOT NULL,
	[patient_id] [int] NOT NULL,
	[relation_type_num] [int] NOT NULL,
	[date_from] [datetime] NOT NULL DEFAULT current_timestamp,
	[date_to] [datetime] NULL,
 CONSTRAINT [PK__P_Relati__3B1854526166761E] PRIMARY KEY CLUSTERED 
(
	[relation_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[P_Prescriptions]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[P_Prescriptions](
	[prescription_num] [int] IDENTITY(1000,1) NOT NULL,
	[medication_num] [int] NOT NULL,
	[dosage] [float] NOT NULL,
	[doctor_id] [int] NOT NULL,
	[patient_id] [int] NOT NULL,
	[date_from] [datetime] NULL,
	[date_to] [datetime] NULL,
 CONSTRAINT [PK__P_Prescr__263FCD83787EE5A0] PRIMARY KEY CLUSTERED 
(
	[prescription_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[O_EmergencyMedicationUse]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[O_EmergencyMedicationUse](
	[emergency_medication_use_num] [int] IDENTITY(1000,1) NOT NULL,
	[event_id] [int] NOT NULL,
	[providing_member_id] [int] NOT NULL,
	[providing_dispenser_num] [int] NULL,
	[approved_by_id] [int] NOT NULL,
	[medication_num] [int] NOT NULL,
	[approval_date] [datetime] NULL,
	[medication_provision_date] [datetime] NULL,
	[digital_signature_file] [varchar](100) NULL,
 CONSTRAINT [PK__O_Emerge__F3CC7B8722751F6C] PRIMARY KEY CLUSTERED 
(
	[emergency_medication_use_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[MP_Affiliation]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[MP_Affiliation](
	[type_history_num] [int] NOT NULL IDENTITY(10000,1),
	[organization_id] [int] NOT NULL,
	[medical_personnel_id] [int] NOT NULL,
	[position_num] [int] NOT NULL,
	[a_date_from] [datetime] NOT NULL default current_timestamp,
	[a_date_to] [datetime] NULL,
 CONSTRAINT [PK__MP_Affil__80B026CF32AB8735] PRIMARY KEY CLUSTERED 
(
	[type_history_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[MP_Affiliation] ON
GO
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1000, 1000, 1000, 1000, CAST(0x0000A48A00C536ED AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1003, 1000, 1001, 1000, CAST(0x0000A4BB008BA835 AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1004, 1000, 1002, 1000, CAST(0x0000A4BB008F8794 AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1005, 1000, 1003, 1000, CAST(0x0000A4BB00958B05 AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1006, 1000, 1004, 1000, CAST(0x0000A4BB00A34F11 AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1007, 1000, 1005, 1000, CAST(0x0000A4BB00B1351F AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1008, 1000, 1006, 1000, CAST(0x0000A4BB00B92ECB AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1009, 1000, 1007, 1000, CAST(0x0000A4BB00B92FF7 AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1010, 1000, 1008, 1000, CAST(0x0000A4BB00BC35B9 AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1011, 1000, 1009, 1000, CAST(0x0000A4BB00BE7EAF AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1012, 1000, 1010, 1000, CAST(0x0000A4BB00BE7EE3 AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1013, 1000, 1011, 1000, CAST(0x0000A4BB00C4CB8D AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1014, 1007, 1012, 1000, CAST(0x0000A4BB00D693F6 AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1015, 1000, 1013, 1000, CAST(0x0000A4BB00D83CEC AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1016, 1000, 1014, 1000, CAST(0x0000A4BF004B50F5 AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1017, 1006, 1015, 1000, CAST(0x0000A4BF00950AC6 AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1018, 1000, 1016, 1000, CAST(0x0000A4BF0099F981 AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1019, 1000, 1017, 1000, CAST(0x0000A4BF009CC7C1 AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1020, 1000, 1018, 1000, CAST(0x0000A4BF00A26EEE AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1021, 1000, 1019, 1000, CAST(0x0000A4BF00A71916 AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1022, 1000, 1020, 1000, CAST(0x0000A4C0008A1142 AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1023, 1000, 1021, 1000, CAST(0x0000A4C000928F16 AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1024, 1000, 1022, 1000, CAST(0x0000A4C000ACCF00 AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1025, 1000, 1023, 1000, CAST(0x0000A4C100BA7A83 AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1026, 1000, 1024, 1000, CAST(0x0000A4C100D65B1A AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1027, 1000, 1025, 1000, CAST(0x0000A4C100F6E0DF AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1028, 1000, 1026, 1000, CAST(0x0000A4C100F9052A AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1029, 1000, 1027, 1000, CAST(0x0000A4C100FC0693 AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1030, 1000, 1028, 1000, CAST(0x0000A4C20041B08D AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1031, 1000, 1030, 1000, CAST(0x0000A4C5002EBA37 AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1032, 1000, 1031, 1000, CAST(0x0000A4C60096D919 AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1033, 1000, 1032, 1000, CAST(0x0000A4C60096D919 AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1034, 1000, 1033, 1000, CAST(0x0000A4C60096D92C AS DateTime), NULL)
INSERT [dbo].[MP_Affiliation] ([type_history_num], [organization_id], [medical_personnel_id], [position_num], [a_date_from], [a_date_to]) VALUES (1035, 1000, 1034, 1000, CAST(0x0000A4C600A19AB1 AS DateTime), NULL)
/****** Object:  Table [dbo].[P_Diagnosis]    Script Date: 08/08/2015 15:40:06 ******/
GO
SET IDENTITY_INSERT [dbo].[MP_Affiliation] OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[P_Diagnosis](
	[diagnosis_num] [int] NOT NULL IDENTITY(10000,1),
	[patient_id] [int] NOT NULL,
	[medical_condition_id] [int] NOT NULL,
	[doctor_id] [int] NOT NULL,
	[date_from] [datetime] NULL,
	[date_to] [datetime] NULL,
 CONSTRAINT [PK__P_Diagno__43A96B0408B54D69] PRIMARY KEY CLUSTERED 
(
	[diagnosis_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[P_Diagnosis] ON
GO
INSERT [dbo].[P_Diagnosis] ([diagnosis_num], [patient_id], [medical_condition_id], [doctor_id], [date_from], [date_to]) VALUES (1000, 1000, 1000, 1000, CAST(0x0000A48A00B9C0C4 AS DateTime), NULL)
INSERT [dbo].[P_Diagnosis] ([diagnosis_num], [patient_id], [medical_condition_id], [doctor_id], [date_from], [date_to]) VALUES (1001, 1009, 1000, 1018, CAST(0x0000A4C00065D763 AS DateTime), CAST(0x0000D73D00000000 AS DateTime))
INSERT [dbo].[P_Diagnosis] ([diagnosis_num], [patient_id], [medical_condition_id], [doctor_id], [date_from], [date_to]) VALUES (1003, 1020, 1000, 1018, CAST(0x0000A4C50059FFE0 AS DateTime), CAST(0x00007EFA00000000 AS DateTime))
INSERT [dbo].[P_Diagnosis] ([diagnosis_num], [patient_id], [medical_condition_id], [doctor_id], [date_from], [date_to]) VALUES (1004, 1021, 1000, 1018, CAST(0x0000A4C5006174B6 AS DateTime), CAST(0x00007EFA00000000 AS DateTime))
INSERT [dbo].[P_Diagnosis] ([diagnosis_num], [patient_id], [medical_condition_id], [doctor_id], [date_from], [date_to]) VALUES (1005, 1022, 1000, 1018, CAST(0x0000A4C600A12AF1 AS DateTime), CAST(0x00007EFA00000000 AS DateTime))
INSERT [dbo].[P_Diagnosis] ([diagnosis_num], [patient_id], [medical_condition_id], [doctor_id], [date_from], [date_to]) VALUES (1006, 1029, 1000, 1018, CAST(0x0000A4C600C48584 AS DateTime), CAST(0x0000A9C800000000 AS DateTime))
INSERT [dbo].[P_Diagnosis] ([diagnosis_num], [patient_id], [medical_condition_id], [doctor_id], [date_from], [date_to]) VALUES (1007, 1030, 1000, 1018, CAST(0x0000A4C600C6BD54 AS DateTime), CAST(0x0000A6EE00000000 AS DateTime))
INSERT [dbo].[P_Diagnosis] ([diagnosis_num], [patient_id], [medical_condition_id], [doctor_id], [date_from], [date_to]) VALUES (1008, 1037, 1000, 1018, CAST(0x0000A4C700C328B8 AS DateTime), CAST(0x0000A41300000000 AS DateTime))
INSERT [dbo].[P_Diagnosis] ([diagnosis_num], [patient_id], [medical_condition_id], [doctor_id], [date_from], [date_to]) VALUES (1009, 1040, 1000, 1018, CAST(0x0000A4C700C98500 AS DateTime), CAST(0x0000654C00000000 AS DateTime))
INSERT [dbo].[P_Diagnosis] ([diagnosis_num], [patient_id], [medical_condition_id], [doctor_id], [date_from], [date_to]) VALUES (1010, 1041, 1000, 1018, CAST(0x0000A4C700DCA64E AS DateTime), CAST(0x0000806800000000 AS DateTime))
INSERT [dbo].[P_Diagnosis] ([diagnosis_num], [patient_id], [medical_condition_id], [doctor_id], [date_from], [date_to]) VALUES (1011, 1042, 1000, 1018, CAST(0x0000A4C8007E880B AS DateTime), CAST(0x00007D8D00000000 AS DateTime))
INSERT [dbo].[P_Diagnosis] ([diagnosis_num], [patient_id], [medical_condition_id], [doctor_id], [date_from], [date_to]) VALUES (1012, 1043, 1000, 1018, CAST(0x0000A4C80080C601 AS DateTime), CAST(0x00007D8D00000000 AS DateTime))
/****** Object:  Table [dbo].[O_EmergencyEventActions]    Script Date: 08/08/2015 15:40:06 ******/
GO
SET IDENTITY_INSERT [dbo].[P_Diagnosis] OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[O_EmergencyEventActions](
	[emergency_action_num] [int] NOT NULL IDENTITY(10000,1),
	[event_id] [int] NOT NULL,
	[action_type_num] [int] NOT NULL,
	[created_date] [timestamp] NOT NULL,
	[more_description] [nvarchar(500)],
 CONSTRAINT [PK__O_Emerge__AC0BBFFF2B0A656D] PRIMARY KEY CLUSTERED 
(
	[emergency_action_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[P_Supervision]    Script Date: 08/08/2015 15:40:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[P_Supervision](
	[treatment_num] [int] NOT NULL IDENTITY(10000,1),
	[doctor_id] [int] NOT NULL,
	[patient_id] [int] NOT NULL,
	[date_from] [datetime] NULL,
	[date_to] [datetime] NULL,
 CONSTRAINT [PK__P_Superv__037553D35224328E] PRIMARY KEY CLUSTERED 
(
	[treatment_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[P_Supervision] ON
GO
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1000, 1000, 1000, CAST(0x0000A48A00B7C79C AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1002, 1000, 1002, CAST(0x0000A4BE00885B8B AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1004, 1018, 1007, CAST(0x0000A4C0005FC35B AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1005, 1018, 1008, CAST(0x0000A4C000647D8A AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1006, 1018, 1009, CAST(0x0000A4C00065D5AF AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1007, 1018, 1010, CAST(0x0000A4C200A8C0A1 AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1008, 1018, 1011, CAST(0x0000A4C30042C20C AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1009, 1018, 1012, CAST(0x0000A4C30043409C AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1010, 1018, 1013, CAST(0x0000A4C300437257 AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1011, 1018, 1014, CAST(0x0000A4C30043D31C AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1012, 1018, 1015, CAST(0x0000A4C3004706EE AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1013, 1018, 1016, CAST(0x0000A4C30047D0BB AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1014, 1018, 1017, CAST(0x0000A4C5005421D8 AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1015, 1018, 1018, CAST(0x0000A4C500555A7F AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1016, 1018, 1019, CAST(0x0000A4C500592894 AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1017, 1018, 1020, CAST(0x0000A4C50059EDC9 AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1018, 1018, 1021, CAST(0x0000A4C500617302 AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1019, 1018, 1022, CAST(0x0000A4C600A12942 AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1020, 1018, 1023, CAST(0x0000A4C600A19038 AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1021, 1018, 1024, CAST(0x0000A4C600BB830C AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1022, 1018, 1025, CAST(0x0000A4C600BB9DD6 AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1023, 1018, 1026, CAST(0x0000A4C600BCA5ED AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1024, 1018, 1027, CAST(0x0000A4C600BE99C5 AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1025, 1018, 1028, CAST(0x0000A4C600C05D95 AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1026, 1018, 1029, CAST(0x0000A4C600C483D5 AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1027, 1018, 1030, CAST(0x0000A4C600C6BB9F AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1028, 1018, 1031, CAST(0x0000A4C600CD6BB7 AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1029, 1018, 1032, CAST(0x0000A4C600CFBCBD AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1030, 1018, 1033, CAST(0x0000A4C600D191F2 AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1031, 1018, 1034, CAST(0x0000A4C600D50146 AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1032, 1018, 1035, CAST(0x0000A4C600E0532C AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1033, 1018, 1036, CAST(0x0000A4C700BB54D9 AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1034, 1018, 1037, CAST(0x0000A4C700C3270D AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1035, 1018, 1038, CAST(0x0000A4C700C4BA9E AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1036, 1018, 1039, CAST(0x0000A4C700C71DB6 AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1037, 1018, 1040, CAST(0x0000A4C700C9835F AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1038, 1018, 1041, CAST(0x0000A4C700DCA4B1 AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1039, 1018, 1042, CAST(0x0000A4C8007E8660 AS DateTime), NULL)
INSERT [dbo].[P_Supervision] ([treatment_num], [doctor_id], [patient_id], [date_from], [date_to]) VALUES (1040, 1018, 1043, CAST(0x0000A4C80080C460 AS DateTime), NULL)
/****** Object:  Table [dbo].[O_EmergencyEventResponse]    Script Date: 08/08/2015 15:40:06 ******/
GO
SET IDENTITY_INSERT [dbo].[P_Supervision] OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[O_EmergencyEventResponse](
	[response_num] [int] IDENTITY(1000,1) NOT NULL,
	[community_member_id] [int] NOT NULL,
	[event_id] [int] NOT NULL,
	[prescription_num] [int] NOT NULL,
	[eta_by_foot] [int] NOT NULL,
	[eta_by_car] [int] NOT NULL,
	[created_date] [timestamp] NOT NULL,
	[location_remark] [varchar](250) NOT NULL,
	[request_sent_date] [datetime] NOT NULL,
	[response_date] [datetime] NULL,
	[response_type] [int] NOT NULL,
	[transformation_mean] [int] NULL,
	[activation_date] [datetime] NULL,
	[arrival_date] [datetime] NULL,
	[result] [varchar](100) NULL,
 CONSTRAINT [PK__O_Emerge__F44D3BEE00200768] PRIMARY KEY CLUSTERED 
(
	[response_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Default [DF__O_Emergen__reque__04E4BC85]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[O_EmergencyEventResponse] ADD  CONSTRAINT [DF__O_Emergen__reque__04E4BC85]  DEFAULT (getdate()) FOR [request_sent_date]
GO
/****** Object:  Default [DF__O_Emergen__respo__05D8E0BE]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[O_EmergencyEventResponse] ADD  CONSTRAINT [DF__O_Emergen__respo__05D8E0BE]  DEFAULT ((0)) FOR [response_type]
GO
/****** Object:  Default [DF__O_Emergen__last___5CD6CB2B]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[O_EmergencyEvents] ADD  CONSTRAINT [DF__O_Emergen__last___5CD6CB2B]  DEFAULT (getdate()) FOR [last_action_time]
GO
/****** Object:  Default [DF__O_Emergen__appro__29221CFB]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[O_EmergencyMedicationUse] ADD  CONSTRAINT [DF__O_Emergen__appro__29221CFB]  DEFAULT (getdate()) FOR [approval_date]
GO
/****** Object:  Default [DF__P_Prescri__date___7D439ABD]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[P_Prescriptions] ADD  CONSTRAINT [DF__P_Prescri__date___7D439ABD]  DEFAULT (getdate()) FOR [date_from]
GO
/****** Object:  ForeignKey [FK__Availabil__commu__3B75D760]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[Availability]  WITH CHECK ADD  CONSTRAINT [FK__Availabil__commu__3B75D760] FOREIGN KEY([community_member_id])
REFERENCES [dbo].[P_CommunityMembers] ([community_member_id])
GO
ALTER TABLE [dbo].[Availability] CHECK CONSTRAINT [FK__Availabil__commu__3B75D760]
GO
/****** Object:  ForeignKey [FK_B_DecisionGivenAmbulaneETA_M_MedicalConditions]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[B_DecisionGivenAmbulaneETA]  WITH CHECK ADD  CONSTRAINT [FK_B_DecisionGivenAmbulaneETA_M_MedicalConditions] FOREIGN KEY([medical_condition_id])
REFERENCES [dbo].[M_MedicalConditions] ([medical_condition_id])
GO
ALTER TABLE [dbo].[B_DecisionGivenAmbulaneETA] CHECK CONSTRAINT [FK_B_DecisionGivenAmbulaneETA_M_MedicalConditions]
GO
/****** Object:  ForeignKey [FK_Frequencies_M_MedicalConditions]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[Frequencies]  WITH CHECK ADD  CONSTRAINT [FK_Frequencies_M_MedicalConditions] FOREIGN KEY([medical_condition_id])
REFERENCES [dbo].[M_MedicalConditions] ([medical_condition_id])
GO
ALTER TABLE [dbo].[Frequencies] CHECK CONSTRAINT [FK_Frequencies_M_MedicalConditions]
GO
/****** Object:  ForeignKey [FK_M_Compositions_M_ActiveComponents]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[M_Compositions]  WITH CHECK ADD  CONSTRAINT [FK_M_Compositions_M_ActiveComponents] FOREIGN KEY([active_component_id])
REFERENCES [dbo].[M_ActiveComponents] ([active_component_id])
GO
ALTER TABLE [dbo].[M_Compositions] CHECK CONSTRAINT [FK_M_Compositions_M_ActiveComponents]
GO
/****** Object:  ForeignKey [FK_M_Compositions_M_BrandNames]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[M_Compositions]  WITH CHECK ADD  CONSTRAINT [FK_M_Compositions_M_BrandNames] FOREIGN KEY([brand_name_id])
REFERENCES [dbo].[M_BrandNames] ([brand_name_id])
GO
ALTER TABLE [dbo].[M_Compositions] CHECK CONSTRAINT [FK_M_Compositions_M_BrandNames]
GO
/****** Object:  ForeignKey [FK_M_Indications_M_BrandNames]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[M_Indications]  WITH CHECK ADD  CONSTRAINT [FK_M_Indications_M_BrandNames] FOREIGN KEY([brand_name_id])
REFERENCES [dbo].[M_BrandNames] ([brand_name_id])
GO
ALTER TABLE [dbo].[M_Indications] CHECK CONSTRAINT [FK_M_Indications_M_BrandNames]
GO
/****** Object:  ForeignKey [FK_M_Indications_M_MedicalConditions]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[M_Indications]  WITH CHECK ADD  CONSTRAINT [FK_M_Indications_M_MedicalConditions] FOREIGN KEY([medical_condition_id])
REFERENCES [dbo].[M_MedicalConditions] ([medical_condition_id])
GO
ALTER TABLE [dbo].[M_Indications] CHECK CONSTRAINT [FK_M_Indications_M_MedicalConditions]
GO
/****** Object:  ForeignKey [FK__M_Substit__brand__40F9A68C]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[M_Substitutives]  WITH CHECK ADD  CONSTRAINT [FK__M_Substit__brand__40F9A68C] FOREIGN KEY([brand_name_id1])
REFERENCES [dbo].[M_BrandNames] ([brand_name_id])
GO
ALTER TABLE [dbo].[M_Substitutives] CHECK CONSTRAINT [FK__M_Substit__brand__40F9A68C]
GO
/****** Object:  ForeignKey [FK__M_Substit__brand__41EDCAC5]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[M_Substitutives]  WITH CHECK ADD  CONSTRAINT [FK__M_Substit__brand__41EDCAC5] FOREIGN KEY([brand_name_id2])
REFERENCES [dbo].[M_BrandNames] ([brand_name_id])
GO
ALTER TABLE [dbo].[M_Substitutives] CHECK CONSTRAINT [FK__M_Substit__brand__41EDCAC5]
GO
/****** Object:  ForeignKey [FK__MP_Affili__organ__3C34F16F]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[MP_Affiliation]  WITH CHECK ADD  CONSTRAINT [FK__MP_Affili__organ__3C34F16F] FOREIGN KEY([organization_id])
REFERENCES [dbo].[MP_Organizations] ([organization_id])
GO
ALTER TABLE [dbo].[MP_Affiliation] CHECK CONSTRAINT [FK__MP_Affili__organ__3C34F16F]
GO
/****** Object:  ForeignKey [FK__MP_Certif__speci__3B40CD36]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[MP_Certification]  WITH CHECK ADD  CONSTRAINT [FK__MP_Certif__speci__3B40CD36] FOREIGN KEY([specialization_id])
REFERENCES [dbo].[MP_Specialization] ([specialization_id])
GO
ALTER TABLE [dbo].[MP_Certification] CHECK CONSTRAINT [FK__MP_Certif__speci__3B40CD36]
GO
/****** Object:  ForeignKey [FK__MP_Medica__commu__3F466844]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[MP_MedicalPersonnel]  WITH CHECK ADD  CONSTRAINT [FK__MP_Medica__commu__3F466844] FOREIGN KEY([community_member_id])
REFERENCES [dbo].[P_CommunityMembers] ([community_member_id])
GO
ALTER TABLE [dbo].[MP_MedicalPersonnel] CHECK CONSTRAINT [FK__MP_Medica__commu__3F466844]
GO
/****** Object:  ForeignKey [FK__MP_Organi__organ__367C1819]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[MP_Organizations]  WITH CHECK ADD  CONSTRAINT [FK__MP_Organi__organ__367C1819] FOREIGN KEY([organization_type_num])
REFERENCES [dbo].[MP_OrganizationTypes] ([organization_type_num])
GO
ALTER TABLE [dbo].[MP_Organizations] CHECK CONSTRAINT [FK__MP_Organi__organ__367C1819]
GO
/****** Object:  ForeignKey [FK__O_Emergen__actio__30C33EC3]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[O_EmergencyEventActions]  WITH CHECK ADD  CONSTRAINT [FK__O_Emergen__actio__30C33EC3] FOREIGN KEY([action_status_num])
REFERENCES [dbo].[O_ActionStatus] ([action_status_num])
GO
ALTER TABLE [dbo].[O_EmergencyEventActions] CHECK CONSTRAINT [FK__O_Emergen__actio__30C33EC3]
GO
/****** Object:  ForeignKey [FK__O_Emergen__event__2CF2ADDF]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[O_EmergencyEventActions]  WITH CHECK ADD  CONSTRAINT [FK__O_Emergen__event__2CF2ADDF] FOREIGN KEY([event_id])
REFERENCES [dbo].[O_EmergencyEvents] ([event_id])
GO
ALTER TABLE [dbo].[O_EmergencyEventActions] CHECK CONSTRAINT [FK__O_Emergen__event__2CF2ADDF]
GO
/****** Object:  ForeignKey [FK__O_Emergen__commu__02084FDA]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[O_EmergencyEventResponse]  WITH CHECK ADD  CONSTRAINT [FK__O_Emergen__commu__02084FDA] FOREIGN KEY([community_member_id])
REFERENCES [dbo].[P_CommunityMembers] ([community_member_id])
GO
ALTER TABLE [dbo].[O_EmergencyEventResponse] CHECK CONSTRAINT [FK__O_Emergen__commu__02084FDA]
GO
/****** Object:  ForeignKey [FK__O_Emergen__event__02FC7413]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[O_EmergencyEventResponse]  WITH CHECK ADD  CONSTRAINT [FK__O_Emergen__event__02FC7413] FOREIGN KEY([event_id])
REFERENCES [dbo].[O_EmergencyEvents] ([event_id])
GO
ALTER TABLE [dbo].[O_EmergencyEventResponse] CHECK CONSTRAINT [FK__O_Emergen__event__02FC7413]
GO
/****** Object:  ForeignKey [FK__O_Emergen__presc__03F0984C]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[O_EmergencyEventResponse]  WITH CHECK ADD  CONSTRAINT [FK__O_Emergen__presc__03F0984C] FOREIGN KEY([prescription_num])
REFERENCES [dbo].[P_Prescriptions] ([prescription_num])
GO
ALTER TABLE [dbo].[O_EmergencyEventResponse] CHECK CONSTRAINT [FK__O_Emergen__presc__03F0984C]
GO
/****** Object:  ForeignKey [FK__O_Emergen__creat__59063A47]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[O_EmergencyEvents]  WITH CHECK ADD  CONSTRAINT [FK__O_Emergen__creat__59063A47] FOREIGN KEY([create_by_member_id])
REFERENCES [dbo].[P_CommunityMembers] ([community_member_id])
GO
ALTER TABLE [dbo].[O_EmergencyEvents] CHECK CONSTRAINT [FK__O_Emergen__creat__59063A47]
GO
/****** Object:  ForeignKey [FK__O_Emergen__ems_m__5AEE82B9]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[O_EmergencyEvents]  WITH CHECK ADD  CONSTRAINT [FK__O_Emergen__ems_m__5AEE82B9] FOREIGN KEY([ems_member_id])
REFERENCES [dbo].[P_CommunityMembers] ([community_member_id])
GO
ALTER TABLE [dbo].[O_EmergencyEvents] CHECK CONSTRAINT [FK__O_Emergen__ems_m__5AEE82B9]
GO
/****** Object:  ForeignKey [FK__O_Emergen__medic__59FA5E80]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[O_EmergencyEvents]  WITH CHECK ADD  CONSTRAINT [FK__O_Emergen__medic__59FA5E80] FOREIGN KEY([medical_condition_id])
REFERENCES [dbo].[M_MedicalConditions] ([medical_condition_id])
GO
ALTER TABLE [dbo].[O_EmergencyEvents] CHECK CONSTRAINT [FK__O_Emergen__medic__59FA5E80]
GO
/****** Object:  ForeignKey [FK__O_Emergen__statu__5BE2A6F2]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[O_EmergencyEvents]  WITH CHECK ADD  CONSTRAINT [FK__O_Emergen__statu__5BE2A6F2] FOREIGN KEY([status_num])
REFERENCES [dbo].[O_EventStatuses] ([status_num])
GO
ALTER TABLE [dbo].[O_EmergencyEvents] CHECK CONSTRAINT [FK__O_Emergen__statu__5BE2A6F2]
GO
/****** Object:  ForeignKey [FK__O_Emergen__appro__2739D489]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[O_EmergencyMedicationUse]  WITH CHECK ADD  CONSTRAINT [FK__O_Emergen__appro__2739D489] FOREIGN KEY([approved_by_id])
REFERENCES [dbo].[P_CommunityMembers] ([community_member_id])
GO
ALTER TABLE [dbo].[O_EmergencyMedicationUse] CHECK CONSTRAINT [FK__O_Emergen__appro__2739D489]
GO
/****** Object:  ForeignKey [FK__O_Emergen__event__245D67DE]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[O_EmergencyMedicationUse]  WITH CHECK ADD  CONSTRAINT [FK__O_Emergen__event__245D67DE] FOREIGN KEY([event_id])
REFERENCES [dbo].[O_EmergencyEvents] ([event_id])
GO
ALTER TABLE [dbo].[O_EmergencyMedicationUse] CHECK CONSTRAINT [FK__O_Emergen__event__245D67DE]
GO
/****** Object:  ForeignKey [FK__O_Emergen__medic__282DF8C2]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[O_EmergencyMedicationUse]  WITH CHECK ADD  CONSTRAINT [FK__O_Emergen__medic__282DF8C2] FOREIGN KEY([medication_num])
REFERENCES [dbo].[P_Medications] ([medication_num])
GO
ALTER TABLE [dbo].[O_EmergencyMedicationUse] CHECK CONSTRAINT [FK__O_Emergen__medic__282DF8C2]
GO
/****** Object:  ForeignKey [FK__O_Emergen__provi__25518C17]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[O_EmergencyMedicationUse]  WITH CHECK ADD  CONSTRAINT [FK__O_Emergen__provi__25518C17] FOREIGN KEY([providing_member_id])
REFERENCES [dbo].[P_CommunityMembers] ([community_member_id])
GO
ALTER TABLE [dbo].[O_EmergencyMedicationUse] CHECK CONSTRAINT [FK__O_Emergen__provi__25518C17]
GO
/****** Object:  ForeignKey [FK__O_Emergen__provi__2645B050]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[O_EmergencyMedicationUse]  WITH CHECK ADD  CONSTRAINT [FK__O_Emergen__provi__2645B050] FOREIGN KEY([providing_dispenser_num])
REFERENCES [dbo].[O_AutomaticDispensers] ([dispensers_num])
GO
ALTER TABLE [dbo].[O_EmergencyMedicationUse] CHECK CONSTRAINT [FK__O_Emergen__provi__2645B050]
GO
/****** Object:  ForeignKey [FK__P_Diagnos__docto__0B91BA14]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[P_Diagnosis]  WITH CHECK ADD  CONSTRAINT [FK__P_Diagnos__docto__0B91BA14] FOREIGN KEY([doctor_id])
REFERENCES [dbo].[P_Doctors] ([doctor_id])
GO
ALTER TABLE [dbo].[P_Diagnosis] CHECK CONSTRAINT [FK__P_Diagnos__docto__0B91BA14]
GO
/****** Object:  ForeignKey [FK__P_Diagnos__patie__0A9D95DB]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[P_Diagnosis]  WITH CHECK ADD  CONSTRAINT [FK__P_Diagnos__patie__0A9D95DB] FOREIGN KEY([patient_id])
REFERENCES [dbo].[P_Patients] ([patient_id])
GO
ALTER TABLE [dbo].[P_Diagnosis] CHECK CONSTRAINT [FK__P_Diagnos__patie__0A9D95DB]
GO
/****** Object:  ForeignKey [FK_P_Doctors_P_CommunityMembers]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[P_Doctors]  WITH CHECK ADD  CONSTRAINT [FK_P_Doctors_P_CommunityMembers] FOREIGN KEY([community_member_id])
REFERENCES [dbo].[P_CommunityMembers] ([community_member_id])
GO
ALTER TABLE [dbo].[P_Doctors] CHECK CONSTRAINT [FK_P_Doctors_P_CommunityMembers]
GO
/****** Object:  ForeignKey [FK_P_Patients_P_CommunityMembers]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[P_Patients]  WITH CHECK ADD  CONSTRAINT [FK_P_Patients_P_CommunityMembers] FOREIGN KEY([community_member_id])
REFERENCES [dbo].[P_CommunityMembers] ([community_member_id])
GO
ALTER TABLE [dbo].[P_Patients] CHECK CONSTRAINT [FK_P_Patients_P_CommunityMembers]
GO
/****** Object:  ForeignKey [FK__P_Prescri__docto__7B5B524B]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[P_Prescriptions]  WITH CHECK ADD  CONSTRAINT [FK__P_Prescri__docto__7B5B524B] FOREIGN KEY([doctor_id])
REFERENCES [dbo].[P_Doctors] ([doctor_id])
GO
ALTER TABLE [dbo].[P_Prescriptions] CHECK CONSTRAINT [FK__P_Prescri__docto__7B5B524B]
GO
/****** Object:  ForeignKey [FK__P_Prescri__medic__7A672E12]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[P_Prescriptions]  WITH CHECK ADD  CONSTRAINT [FK__P_Prescri__medic__7A672E12] FOREIGN KEY([medication_num])
REFERENCES [dbo].[P_Medications] ([medication_num])
GO
ALTER TABLE [dbo].[P_Prescriptions] CHECK CONSTRAINT [FK__P_Prescri__medic__7A672E12]
GO
/****** Object:  ForeignKey [FK__P_Prescri__patie__7C4F7684]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[P_Prescriptions]  WITH CHECK ADD  CONSTRAINT [FK__P_Prescri__patie__7C4F7684] FOREIGN KEY([patient_id])
REFERENCES [dbo].[P_Patients] ([patient_id])
GO
ALTER TABLE [dbo].[P_Prescriptions] CHECK CONSTRAINT [FK__P_Prescri__patie__7C4F7684]
GO
/****** Object:  ForeignKey [FK__P_Relatio__commu__634EBE90]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[P_Relations]  WITH CHECK ADD  CONSTRAINT [FK__P_Relatio__commu__634EBE90] FOREIGN KEY([community_member_id])
REFERENCES [dbo].[P_CommunityMembers] ([community_member_id])
GO
ALTER TABLE [dbo].[P_Relations] CHECK CONSTRAINT [FK__P_Relatio__commu__634EBE90]
GO
/****** Object:  ForeignKey [FK__P_Relatio__patie__6442E2C9]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[P_Relations]  WITH CHECK ADD  CONSTRAINT [FK__P_Relatio__patie__6442E2C9] FOREIGN KEY([patient_id])
REFERENCES [dbo].[P_Patients] ([patient_id])
GO
ALTER TABLE [dbo].[P_Relations] CHECK CONSTRAINT [FK__P_Relatio__patie__6442E2C9]
GO
/****** Object:  ForeignKey [FK__P_Relatio__relat__662B2B3B]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[P_Relations]  WITH CHECK ADD  CONSTRAINT [FK__P_Relatio__relat__662B2B3B] FOREIGN KEY([relation_type_num])
REFERENCES [dbo].[P_RelationTypes] ([relation_type_num])
GO
ALTER TABLE [dbo].[P_Relations] CHECK CONSTRAINT [FK__P_Relatio__relat__662B2B3B]
GO
/****** Object:  ForeignKey [FK__P_StatusL__commu__5CA1C101]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[P_StatusLog]  WITH CHECK ADD  CONSTRAINT [FK__P_StatusL__commu__5CA1C101] FOREIGN KEY([community_member_id])
REFERENCES [dbo].[P_CommunityMembers] ([community_member_id])
GO
ALTER TABLE [dbo].[P_StatusLog] CHECK CONSTRAINT [FK__P_StatusL__commu__5CA1C101]
GO
/****** Object:  ForeignKey [FK__P_StatusL__statu__5BAD9CC8]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[P_StatusLog]  WITH CHECK ADD  CONSTRAINT [FK__P_StatusL__statu__5BAD9CC8] FOREIGN KEY([status_num])
REFERENCES [dbo].[P_Statuses] ([status_num])
GO
ALTER TABLE [dbo].[P_StatusLog] CHECK CONSTRAINT [FK__P_StatusL__statu__5BAD9CC8]
GO
/****** Object:  ForeignKey [FK__P_Supervi__docto__540C7B00]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[P_Supervision]  WITH CHECK ADD  CONSTRAINT [FK__P_Supervi__docto__540C7B00] FOREIGN KEY([doctor_id])
REFERENCES [dbo].[P_Doctors] ([doctor_id])
GO
ALTER TABLE [dbo].[P_Supervision] CHECK CONSTRAINT [FK__P_Supervi__docto__540C7B00]
GO
/****** Object:  ForeignKey [FK__P_Supervi__patie__55009F39]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[P_Supervision]  WITH CHECK ADD  CONSTRAINT [FK__P_Supervi__patie__55009F39] FOREIGN KEY([patient_id])
REFERENCES [dbo].[P_Patients] ([patient_id])
GO
ALTER TABLE [dbo].[P_Supervision] CHECK CONSTRAINT [FK__P_Supervi__patie__55009F39]
GO
/****** Object:  ForeignKey [FK__P_TypeLog__commu__503BEA1C]    Script Date: 08/08/2015 15:40:06 ******/
ALTER TABLE [dbo].[P_TypeLog]  WITH CHECK ADD  CONSTRAINT [FK__P_TypeLog__commu__503BEA1C] FOREIGN KEY([community_member_id])
REFERENCES [dbo].[P_CommunityMembers] ([community_member_id])
GO
ALTER TABLE [dbo].[P_TypeLog] CHECK CONSTRAINT [FK__P_TypeLog__commu__503BEA1C]
GO
