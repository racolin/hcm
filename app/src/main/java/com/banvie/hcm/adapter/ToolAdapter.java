package com.banvie.hcm.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banvie.hcm.R;
import com.banvie.hcm.type.ToolsType;
import com.banvie.hcm.dialog.MoreToolsDialog;
import com.banvie.hcm.model.Tool;

import java.util.List;

public class ToolAdapter extends RecyclerView.Adapter<ToolAdapter.ToolHolder> {

    List<Tool> tools;
    Context context;
    ToolsType type;

    public ToolAdapter(Context context, List<Tool> tools, ToolsType type) {
        this.type = type;
        this.tools = tools;
        this.context = context;
    }

    public void removeTool(Tool tool) {
        int index = tools.indexOf(tool);
        if (index != -1) {
            tools.remove(index);
            notifyDataSetChanged();
        }
    }

    public void addTool(Tool tool) {
        if (!tools.contains(tool)) {
            tools.add(tool);
            notifyDataSetChanged();
        }

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

        final int i = position;

        switch (type) {
            case HIDING:
                holder.iv_status.setImageResource(R.drawable.ic_delete);
                holder.iv_status.setBackgroundColor(context.getColor(R.color.red));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                break;
            case SHOWING:
                holder.iv_status.setImageResource(R.drawable.ic_plus);
                holder.iv_status.setBackgroundColor(context.getColor(R.color.blue_dark));
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
