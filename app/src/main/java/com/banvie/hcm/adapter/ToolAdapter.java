package com.banvie.hcm.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banvie.hcm.R;
import com.banvie.hcm.listener.OnClickAddOrRemoveToolListener;
import com.banvie.hcm.type.ToolsType;
import com.banvie.hcm.dialog.MoreToolsDialog;
import com.banvie.hcm.model.Tool;

import java.util.List;

public class ToolAdapter extends RecyclerView.Adapter<ToolAdapter.ToolHolder> {

    List<Tool> tools;
    Context context;
    ToolsType type;
    OnClickAddOrRemoveToolListener listener;

    public ToolAdapter(Context context, List<Tool> tools, ToolsType type) {
        this.type = type;
        this.tools = tools;
        this.context = context;
    }

    public ToolAdapter(Context context, OnClickAddOrRemoveToolListener listener, List<Tool> tools, ToolsType type) {
        this.type = type;
        this.tools = tools;
        this.context = context;
        this.listener = listener;
    }

    public void addTool(Tool tool) {
        if (!tools.contains(tool)) {
            tools.add(tool);
            notifyDataSetChanged();
        }
    }

    public void removeTool(Tool tool) {
        int index = tools.indexOf(tool);
        if (index != -1) {
            removeToolFromIndex(index);
        }
    }

    private void removeToolFromIndex(int index) {
        tools.remove(index);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ToolHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ToolHolder(LayoutInflater.from(context).inflate(R.layout.tool, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ToolHolder holder, int position) {
        holder.tv_name.setText(tools.get(position).getName());
        holder.iv_tool.setImageBitmap(BitmapFactory.decodeByteArray(
                tools.get(position).getImage(),
                0,
                tools.get(position).getImage().length));
        holder.iv_status.setBackgroundColor(Color.TRANSPARENT);

        final int i = position;

        switch (type) {
            case HIDING:
                holder.iv_status.setImageResource(R.drawable.ic_plus);
                holder.iv_status.setColorFilter(context.getColor(R.color.blue_dark));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.getSharedPreferences("tools_status", Context.MODE_PRIVATE)
                                .edit().remove(tools.get(i).getName()).commit();
                        tools.get(i).setShow();
                        listener.setOnClickAddOrRemoveTool(tools.get(i));
                        removeToolFromIndex(i);
                    }
                });
                break;
            case SHOWING:
                holder.iv_status.setImageResource(R.drawable.ic_delete);
                holder.iv_status.setColorFilter(context.getColor(R.color.red));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.getSharedPreferences("tools_status", Context.MODE_PRIVATE)
                                .edit().putBoolean(tools.get(i).getName(), true).commit();
                        tools.get(i).setHide();
                        listener.setOnClickAddOrRemoveTool(tools.get(i));
                        removeToolFromIndex(i);
                    }
                });
                break;
            case ALL:
                if (tools.get(i).getName().equals(context.getString(R.string.more_tools))) {
                    holder.itemView.setVisibility(View.GONE);
                }
            case HIGH_LIGHT:
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goToTool(i);
                    }
                });
        }
    }

    private void goToTool(int position) {
        String name = tools.get(position).getName();
        String more = context.getString(R.string.more_tools);
        if (name.equals(more)) {
            MoreToolsDialog dialog = new MoreToolsDialog(context, tools);
            dialog.show();
            return;
        }
        Intent intent = null;
        for (Tool tool : tools) {
            if (tool.getName().equals(name)) {
//                intent = new Intent(context, );
                break;
            }
        }
//        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return tools.size();
    }

    public class ToolHolder extends RecyclerView.ViewHolder {

        ImageView iv_tool;
        TextView tv_name;
        ImageView iv_status;

        public ToolHolder(@NonNull View itemView) {
            super(itemView);
            iv_tool = itemView.findViewById(R.id.iv_tool);
            tv_name = itemView.findViewById(R.id.tv_name);
            iv_status = itemView.findViewById(R.id.iv_status);
        }
    }
}
